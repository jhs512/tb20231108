package com.ll.domain.quotation.export.controller;

import com.ll.global.app.AppTest;
import com.ll.standard.util.Ut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExportControllerTest {
    @BeforeEach
    void beforeEach() {
        AppTest.clear();
    }

    @Test
    @DisplayName("빌드를 하면 data.json 파일이 생성된다.")
    void t1() {
        AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                나의 죽음을 적들에게 알리지 말라.
                이순신
                빌드
                """);

        assertThat(Ut.file.exists("data/data.json")).isTrue();
    }
}