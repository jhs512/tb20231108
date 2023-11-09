package com.ll.standard.util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UtTest {
    private final String filePath = "temp/test.txt";

    @BeforeEach
    void beforeEach() {
        Ut.file.save(filePath, "내용");
    }

    @AfterEach
    void afterEach() {
        Ut.file.delete(filePath);
    }

    @Test
    @DisplayName("파일 생성, 삭제")
    void t1() {
        assertThat(Ut.file.exists(filePath)).isTrue();
    }

    @Test
    @DisplayName("파일내용 읽기")
    void t2() {
        final String content = Ut.file.getContent(filePath);
        assertThat(content).isEqualTo("내용");
    }
}