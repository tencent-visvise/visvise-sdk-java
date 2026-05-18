package com.visvise.sdk.http;

import com.google.gson.JsonParser;
import com.visvise.sdk.exceptions.ErrorFactory;
import com.visvise.sdk.exceptions.WeaverError;
import com.visvise.sdk.models.SSEResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * SSEIterator iterates over SSE events
 */
public class SSEIterator implements Closeable {
    private static final Logger httpLogger = LoggerFactory.getLogger("visvise.http");

    private final HttpURLConnection conn;
    private final BufferedReader reader;
    private String leftover = "";

    public SSEIterator(HTTPClient httpClient, String path, Object body, int readTimeout, String rtx) throws WeaverError {
        String bodyStr = body == null ? "{}" : new com.google.gson.Gson().toJson(body);
        String urlStr = httpClient.getBaseURL() + "/" + path.replaceFirst("^/", "");
        Map<String, String> headers = httpClient.buildHeadersForSSE(bodyStr, rtx);

        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(readTimeout * 1000);
            conn.setReadTimeout(readTimeout * 1000);
            conn.setDoOutput(true);
            conn.setRequestProperty("Accept", "text/event-stream");

            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }

            try (OutputStream os = conn.getOutputStream()) {
                os.write(bodyStr.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();
            httpLogger.debug("POST(SSE) response: status={}, content-type={}", responseCode, conn.getContentType());

            if (responseCode < 200 || responseCode >= 300) {
                String errorBody;
                try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                    StringBuilder sb = new StringBuilder();
                    int ch;
                    int count = 0;
                    while ((ch = br.read()) != -1 && count < 500) {
                        sb.append((char) ch);
                        count++;
                    }
                    errorBody = sb.toString();
                }
                throw ErrorFactory.newNetworkError(
                        String.format("SSE HTTP error [%d]: %s body=%s", responseCode, conn.getResponseMessage(), errorBody));
            }

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw ErrorFactory.newNetworkError(String.format("SSE request failed: %s", e.getMessage()));
        }
    }

    /**
     * Returns the next SSE event
     */
    public SSEResult next() throws WeaverError {
        try {
            String event = "";
            StringBuilder dataBuilder = new StringBuilder();

            while (true) {
                String line;
                if (leftover != null && !leftover.isEmpty()) {
                    line = leftover;
                    leftover = "";
                } else {
                    line = reader.readLine();
                }

                if (line == null) {
                    // EOF
                    if (dataBuilder.length() > 0 || !event.isEmpty()) {
                        return new SSEResult(event, parseData(dataBuilder.toString()));
                    }
                    return null;
                }

                line = line.trim();

                if (line.isEmpty()) {
                    // Empty line indicates end of frame
                    if (!event.isEmpty() || dataBuilder.length() > 0) {
                        return new SSEResult(event, parseData(dataBuilder.toString()));
                    }
                    continue;
                }

                if (line.startsWith(":")) {
                    continue; // Comment
                }

                if (line.startsWith("event:")) {
                    event = line.substring(6).trim();
                } else if (line.startsWith("data:")) {
                    if (dataBuilder.length() > 0) {
                        dataBuilder.append("\n");
                    }
                    dataBuilder.append(line.substring(5).trim());
                } else {
                    // Store for next iteration
                    leftover = line;
                }
            }
        } catch (IOException e) {
            if (e.getMessage() != null && e.getMessage().contains("closed")) {
                // Stream was closed
                if (dataBuilder().length() > 0 || !currentEvent().isEmpty()) {
                    return new SSEResult(currentEvent(), parseData(dataBuilder().toString()));
                }
                return null;
            }
            throw ErrorFactory.newNetworkError(String.format("SSE read error: %s", e.getMessage()));
        }
    }

    private StringBuilder dataBuilder = new StringBuilder();
    private String currentEvent = "";

    private StringBuilder dataBuilder() {
        return dataBuilder;
    }

    private String currentEvent() {
        return currentEvent;
    }

    private Object parseData(String dataStr) {
        if (dataStr == null || dataStr.isEmpty()) {
            return dataStr;
        }
        try {
            return JsonParser.parseString(dataStr);
        } catch (Exception e) {
            return dataStr;
        }
    }

    @Override
    public void close() throws IOException {
        if (reader != null) {
            reader.close();
        }
        if (conn != null) {
            conn.disconnect();
        }
    }
}
