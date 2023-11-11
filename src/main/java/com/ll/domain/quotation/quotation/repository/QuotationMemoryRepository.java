package com.ll.domain.quotation.quotation.repository;

import com.ll.domain.quotation.quotation.entity.Quotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuotationMemoryRepository implements QuotationRepository {
    private final List<Quotation> quotations;
    private long lastQuotationId;

    public QuotationMemoryRepository() {
        quotations = new ArrayList<>();
        lastQuotationId = 0;
    }

    public List<Quotation> findAll() {
        return quotations;
    }

    public void delete(Quotation quotation) {
        quotations.remove(quotation);
    }

    public Optional<Quotation> findById(long id) {
        return quotations
                .stream()
                .filter(quotation -> quotation.getId() == id)
                .findFirst();
    }

    public void save(Quotation quotation) {
        if (quotation.getId() == null) {
            quotation.setId(++lastQuotationId);
            quotations.add(quotation);
        }
    }
}
