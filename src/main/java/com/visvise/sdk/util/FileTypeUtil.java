package com.visvise.sdk.util;

import java.nio.charset.StandardCharsets;

public class FileTypeUtil {

    private static final byte[] PNG = {(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};

    private static final byte[] JPG = {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF};

    private static final byte[] GIF87A = "GIF87a".getBytes(StandardCharsets.US_ASCII);

    private static final byte[] GIF89A = "GIF89a".getBytes(StandardCharsets.US_ASCII);

    private static final byte[] BMP = "BM".getBytes(StandardCharsets.US_ASCII);

    private static final byte[] RIFF = "RIFF".getBytes(StandardCharsets.US_ASCII);

    private static final byte[] WEBP = "WEBP".getBytes(StandardCharsets.US_ASCII);

    private static final byte[] AVI = "AVI ".getBytes(StandardCharsets.US_ASCII);

    private static final byte[] GLTF_BINARY = "glTF".getBytes(StandardCharsets.US_ASCII);

    private static final byte[] FBX_BINARY = "Kaydara FBX Bina".getBytes(StandardCharsets.US_ASCII);

    private static final byte[] FBX_HEADER = "FBXHeaderExtension".getBytes(StandardCharsets.US_ASCII);

    private static final byte[] FTYP = "ftyp".getBytes(StandardCharsets.US_ASCII);

    private static final byte[] QT = "qt  ".getBytes(StandardCharsets.US_ASCII);

    private static final byte[] MOOV = "moov".getBytes(StandardCharsets.US_ASCII);

    private static final byte[] ZIP = {0x50, 0x4B, 0x03, 0x04};

    public static String sniffExtension(byte[] data, String defaultExt) {

        if (data == null || data.length == 0) {
            return normalizeExt(defaultExt);
        }

        // ================= 图片 =================

        if (match(data, 0, PNG)) {
            return ".png";
        }

        if (match(data, 0, JPG)) {
            return ".jpg";
        }

        if (match(data, 0, GIF87A) || match(data, 0, GIF89A)) {
            return ".gif";
        }

        if (match(data, 0, BMP)) {
            return ".bmp";
        }

        if (match(data, 0, RIFF) && match(data, 8, WEBP)) {
            return ".webp";
        }

        if (match(data, 0, new byte[]{0x49, 0x49, 0x2A, 0x00}) ||
                match(data, 0, new byte[]{0x4D, 0x4D, 0x00, 0x2A})) {
            return ".tiff";
        }

        // ================= 3D 模型 =================

        if (match(data, 0, FBX_BINARY)) {
            return ".fbx";
        }

        if (match(data, 0, GLTF_BINARY)) {
            return ".glb";
        }

        if (match(data, 0, FBX_HEADER)) {
            return ".fbx";
        }

        // FBX ASCII
        if (data[0] == ';' && contains(data, 0, Math.min(200, data.length), "FBX")) {
            return ".fbx";
        }

        // OBJ
        int head = skipBlank(data, 0, Math.min(64, data.length));

        if (startsWithText(data, head, "v ") ||
                startsWithText(data, head, "vn ") ||
                startsWithText(data, head, "vt ") ||
                startsWithText(data, head, "f ") ||
                startsWithText(data, head, "o ") ||
                startsWithText(data, head, "g ") ||
                startsWithText(data, head, "mtllib ")) {
            return ".obj";
        }

        // ================= 视频 =================

        if (match(data, 4, FTYP)) {

            if (match(data, 8, QT) || match(data, 8, MOOV)) {
                return ".mov";
            }

            return ".mp4";
        }

        // WebM
        if (match(data, 0,
                new byte[]{0x1A, 0x45, (byte) 0xDF, (byte) 0xA3})) {
            return ".webm";
        }

        // AVI
        if (match(data, 0, RIFF) && match(data, 8, AVI)) {
            return ".avi";
        }

        // ================= 压缩包 =================

        if (match(data, 0, ZIP)) {
            return ".zip";
        }

        // ================= glTF JSON =================

        int jsonStart = skipBlank(data, 0, Math.min(128, data.length));

        if (startsWithText(data, jsonStart, "{") &&
                contains(data, 0, Math.min(256, data.length), "\"asset\"") &&
                contains(data, 0, Math.min(512, data.length), "\"version\"")) {
            return ".gltf";
        }

        // OBJ comment
        if (startsWithText(data, jsonStart, "#") &&
                (contains(data, 0, Math.min(512, data.length), "\nv ") ||
                        contains(data, 0, Math.min(512, data.length), "\no "))) {
            return ".obj";
        }

        return normalizeExt(defaultExt);
    }

    /**
     * 二进制匹配
     */
    private static boolean match(byte[] data, int offset, byte[] target) {

        if (offset < 0 || offset + target.length > data.length) {
            return false;
        }

        for (int i = 0; i < target.length; i++) {
            if (data[offset + i] != target[i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * 文本 startsWith
     */
    private static boolean startsWithText(byte[] data, int offset, String text) {
        byte[] bytes = text.getBytes(StandardCharsets.US_ASCII);
        return match(data, offset, bytes);
    }

    /**
     * 查找字符串
     */
    private static boolean contains(byte[] data,
                                    int start,
                                    int end,
                                    String text) {

        byte[] target = text.getBytes(StandardCharsets.US_ASCII);

        outer:
        for (int i = start; i <= end - target.length; i++) {

            for (int j = 0; j < target.length; j++) {

                if (data[i + j] != target[j]) {
                    continue outer;
                }
            }

            return true;
        }

        return false;
    }

    /**
     * 跳过空白字符
     */
    private static int skipBlank(byte[] data, int start, int end) {

        while (start < end) {

            byte b = data[start];

            if (b == ' ' || b == '\t' || b == '\r' || b == '\n') {
                start++;
            } else {
                break;
            }
        }

        return start;
    }

    /**
     * 标准化扩展名
     */
    private static String normalizeExt(String ext) {

        if (ext == null || ext.isEmpty()) {
            return "";
        }

        return ext.charAt(0) == '.'
                ? ext
                : "." + ext;
    }
}