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
        Ut.file.delete(QuotationFileRepository.QUOTATION_DATA_PATH);
    }

    @Test
    @DisplayName("save 를 하면 quotation 의 id에 새 번호가 할당된다.")
    void t1() {
        final QuotationFileRepository repository = new QuotationFileRepository();
        final Quotation quotation = new Quotation("작가1", "내용1");
        repository.save(quotation); // quotation 의 id 가 1로 할당된다.

        assertThat(quotation.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("1번 명언을 저장하면 테이블 폴더에 1.json이 생긴다.")
    void t2() {
        final QuotationFileRepository repository = new QuotationFileRepository();
        final Quotation quotation = new Quotation("작가1", "내용1");
        repository.save(quotation); // quotation 의 id 가 1로 할당된다.

        assertThat(Ut.file.exists(repository._getQuotationFilePath(quotation))).isTrue();
    }

    @Test
    @DisplayName("1번 명언을 저장 후 다시 불러온다.")
    void t3() {
        final QuotationFileRepository repository = new QuotationFileRepository();
        final Quotation quotation = new Quotation("작가1", "내용1");
        repository.save(quotation); // quotation 의 id 가 1로 할당된다.

        final Quotation quotationFromFile = repository.findById(1L).get();

        assertThat(quotationFromFile).isEqualTo(quotation);
    }
}
