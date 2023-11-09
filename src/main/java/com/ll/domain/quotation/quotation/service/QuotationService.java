package com.ll.domain.quotation.quotation.service;

import com.ll.domain.quotation.quotation.entity.Quotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuotationService {
    private final List<Quotation> quotations;
    private long lastQuotationId;

    public QuotationService() {
        quotations = new ArrayList<>();
        lastQuotationId = 0;
    }

    public List<Quotation> findAll() {
        return quotations;
    }

    public void remove(Quotation quotation) {
        quotations.remove(quotation);
    }

    public Optional<Quotation> findById(long id) {
        return quotations
                .stream()
                .filter(quotation -> quotation.getId() == id)
                .findFirst();
    }

    public void modify(Quotation quotation, String authorName, String content) {
        quotation.setAuthorName(authorName);
        quotation.setContent(content);
    }

    public Quotation write(String authorName, String content) {
        final long id = ++lastQuotationId;

        final Quotation quotation = new Quotation(id, authorName, content);
        quotations.add(quotation);

        return quotation;
    }
}
