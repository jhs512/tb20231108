package com.ll.standard.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.*;

public class Ut {
    public static class file {
        private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

        @SneakyThrows
        public static void save(String filePath, Object obj) {
            // 객체를 JSON 문자열로 직렬화
            String jsonContent = OBJECT_MAPPER.writeValueAsString(obj);

            // JSON 문자열을 파일에 저장
            save(filePath, jsonContent);
        }

        @SneakyThrows
        public static void save(String filePath, String content) {
            final Path path = Paths.get(filePath);

            try {
                Files.writeString(path, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                // 부모 디렉토리가 없어서 발생한 예외인지 확인합니다.
                final Path parentDir = path.getParent();
                if (parentDir != null && Files.notExists(parentDir)) {
                    Files.createDirectories(parentDir);
                    // 디렉토리를 생성한 후 다시 시도합니다.
                    Files.writeString(path, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                } else {
                    // 다른 종류의 IOException이면 그대로 예외를 던집니다.
                    throw e;
                }
            }
        }

        public static boolean exists(String filePath) {
            return Files.exists(Paths.get(filePath));
        }

        @SneakyThrows
        public static boolean delete(String filePath) {
            try {
                Files.delete(Paths.get(filePath));
                return true;
            } catch (NoSuchFileException e) {
                return false;
            }
        }

        @SneakyThrows
        public static String getContent(String filePath) {
            try {
                return Files.readString(Paths.get(filePath));
            } catch (NoSuchFileException e) {
                return null;
            }
        }

        public static long getContentAsLong(String testFilePath, long defaultValue) {
            final String content = getContent(testFilePath);

            if (content == null) return defaultValue;

            try {
                return Long.parseLong(content);
            } catch (NumberFormatException e) {
                return defaultValue;
            }
        }

        public static void save(String filePath, long content) {
            save(filePath, String.valueOf(content));
        }

        @SneakyThrows
        public static <T> T getContent(String filePath, Class<T> cls) {
            final String content = getContent(filePath);

            if (content == null) {
                return null;
            }

            return OBJECT_MAPPER.readValue(content, cls);
        }
    }
}
