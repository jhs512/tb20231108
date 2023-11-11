package com.ll.domain.quotation.quotation.repository;

import com.ll.domain.quotation.quotation.entity.Quotation;

import java.util.List;
import java.util.Optional;

public interface QuotationRepository {
    List<Quotation> findAll();

    void delete(Quotation quotation);

    Optional<Quotation> findById(long id);

    void save(Quotation quotation);
}
