package com.ll.domain.quotation.export.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ll.domain.quotation.quotation.entity.Quotation;
import com.ll.global.app.AppTest;
import com.ll.standard.util.Ut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    @DisplayName("빌드를 하면 data.json 파일이 생성되고 거기에는 모든 명언들의 정보가 있어야 한다.")
    void t2() {
        AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                나의 죽음을 적들에게 알리지 말라.
                이순신
                빌드
                """);

        List<Quotation> quotations = Ut.file.getContent("data/data.json", new TypeReference<>() {
        });

        assertThat(quotations).hasSize(2);

        final Quotation quotation1 = quotations.getFirst();

        assertThat(quotation1.getId()).isEqualTo(1L);
        assertThat(quotation1.getAuthorName()).isEqualTo("작자미상");
        assertThat(quotation1.getContent()).isEqualTo("현재를 사랑하라.");

        final Quotation quotation2 = quotations.getLast();

        assertThat(quotation2.getId()).isEqualTo(2L);
        assertThat(quotation2.getAuthorName()).isEqualTo("이순신");
        assertThat(quotation2.getContent()).isEqualTo("나의 죽음을 적들에게 알리지 말라.");
    }
}