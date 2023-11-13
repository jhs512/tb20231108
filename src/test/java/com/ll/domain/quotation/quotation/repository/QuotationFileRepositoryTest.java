package com.ll.domain.quotation.quotation.repository;

import com.ll.domain.quotation.quotation.entity.Quotation;
import com.ll.standard.util.Ut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuotationFileRepositoryTest {
    @BeforeEach
    void beforeEach() {
        Ut.file.delete("data/prod/quotation");
    }

    @Test
    @DisplayName("save 를 하면 quotation 의 id에 새 번호가 할당된다.")
    void t1() {
        final QuotationFileRepository repository = new QuotationFileRepository();
        final Quotation quotation = new Quotation("작가1", "내용1");
        repository.save(quotation); // quotation 의 id 가 1로 할당된다.

        assertThat(quotation.getId()).isEqualTo(1L);
    }
}
