package com.ll.standard.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UtTest {
    @Test
    @DisplayName("파일 생성")
    void t1() {
        String filePath = "temp/test.txt";
        Ut.file.save(filePath, "내용");

        assertThat(Ut.file.exists(filePath)).isTrue();

        Ut.file.delete(filePath);
    }
}
