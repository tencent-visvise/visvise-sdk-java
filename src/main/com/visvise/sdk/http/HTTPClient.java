package com.visvise.sdk.http;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.visvise.sdk.exceptions.ErrorFactory;
import com.visvise.sdk.exceptions.WeaverError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * HTTPClient is the low-level HTTP client that handles signing and error handling
 */
public class HTTPClient {
    private static final Logger logger = LoggerFactory.getLogger(HTTPClient.class);
    private static final Logger httpLogger = LoggerFactory.getLogger("visvise.http");
    private static final Gson gson = new Gson();

    private final String appId;
    private final String secretKey;
    private final String uid;
    private final String baseURL;
    private final int timeout;
    private boolean debug;

    public HTTPClient(String appId, String secretKey, String uid, String baseURL, int timeout) {
        this.appId = appId;
        this.secretKey = secretKey;
        this.uid = uid;
        this.baseURL = baseURL.endsWith("/") ? baseURL.substring(0, baseURL.length() - 1) : baseURL;
        this.timeout = timeout;
        this.debug = false;
    }

    /**
     * Enable or disable debug logging for this HTTP client
     */
    public void setDebug(boolean enabled) {
        this.debug = enabled;
    }

    /**
     * Check if debug is enabled for this client
     */
    public boolean isDebugEnabled() {
        return this.debug;
    }

    /**
     * Generates the HMAC-SHA256 signature
     */
    private String sign(String bodyStr, String ts) {
        String signStr = bodyStr + ts;
        try {
            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmac.init(secretKeySpec);
            byte[] hash = hmac.doFinal(signStr.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to generate signature", e);
        }
    }

    /**
     * Builds the request headers with signature
     */
    private Map<String, String> buildHeaders(String bodyStr) {
        long ts = System.currentTimeMillis() / 1000;
        String tsStr = String.valueOf(ts);
        String sign = sign(bodyStr, tsStr);

        Map<String, String> headers = new java.util.HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("app_id", appId);
        headers.put("uid", uid);
        headers.put("ts", tsStr);
        headers.put("sign", sign);
        return headers;
    }

    /**
     * Sends a POST request
     */
    public Object post(String path, Object body) throws WeaverError {
        String bodyStr;
        if (body == null) {
            bodyStr = "{}";
        } else {
            bodyStr = gson.toJson(body);
        }

        String urlStr = baseURL + "/" + path.replaceFirst("^/", "");
        Map<String, String> headers = buildHeaders(bodyStr);

        if (isDebugEnabled()) {
            httpLogger.debug("POST request: url={}, body={}", urlStr, truncate(bodyStr, 2000));
        }

        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(timeout * 1000);
            conn.setReadTimeout(timeout * 1000);
            conn.setDoOutput(true);

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }

            try (OutputStream os = conn.getOutputStream()) {
                os.write(bodyStr.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();
            String responseBody;
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            responseCode >= 400 ? conn.getErrorStream() : conn.getInputStream(),
                            StandardCharsets.UTF_8))) {
                responseBody = br.lines().collect(Collectors.joining());
            }

            if (isDebugEnabled()) {
                httpLogger.debug("POST response: url={}, status={}, body={}", urlStr, responseCode, truncate(responseBody, 2000));
            }

            if (responseCode < 200 || responseCode >= 300) {
                throw ErrorFactory.newNetworkError(String.format("HTTP error [%d]: %s", responseCode, responseBody));
            }

            JsonObject result = JsonParser.parseString(responseBody).getAsJsonObject();
            double code = result.has("code") && !result.get("code").isJsonNull()
                    ? result.get("code").getAsDouble() : -1;
            String reqId = result.has("req_id") && !result.get("req_id").isJsonNull()
                    ? result.get("req_id").getAsString() : "";
            String msg = result.has("msg") && !result.get("msg").isJsonNull()
                    ? result.get("msg").getAsString() : "";

            if (code != 0) {
                throw ErrorFactory.raiseForCode((int) code, msg, reqId);
            }

            JsonElement data = result.get("data");
            if (data == null || data.isJsonNull()) {
                return null;
            }
            return data;
        } catch (WeaverError e) {
            throw e;
        } catch (IOException e) {
            throw ErrorFactory.newNetworkError(String.format("Request failed: %s", e.getMessage()));
        }
    }

    /**
     * Truncates a string to maxLen characters
     */
    private String truncate(String s, int maxLen) {
        if (s == null) return null;
        if (s.length() <= maxLen) return s;
        return s.substring(0, maxLen) + "...(truncated)";
    }

    /**
     * Sends a POST request and returns an SSE iterator
     */
    public SSEIterator postSSE(String path, Object body, int readTimeout) throws WeaverError {
        if (readTimeout <= 0) {
            readTimeout = 1200; // Default 20 minutes
        }
        return new SSEIterator(this, path, body, readTimeout);
    }

    public String getBaseURL() {
        return baseURL;
    }

    public Map<String, String> buildHeadersForSSE(String bodyStr) {
        return buildHeaders(bodyStr);
    }
}
