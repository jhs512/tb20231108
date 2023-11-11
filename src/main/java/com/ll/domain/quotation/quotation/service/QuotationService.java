package com.ll.domain.quotation.quotation.service;

import com.ll.domain.quotation.quotation.entity.Quotation;
import com.ll.domain.quotation.quotation.repository.QuotationMemoryRepository;
import com.ll.domain.quotation.quotation.repository.QuotationRepository;

import java.util.List;
import java.util.Optional;

public class QuotationService {
    private final QuotationRepository quotationRepository;

    public QuotationService() {
        quotationRepository = new QuotationMemoryRepository();
    }

    public List<Quotation> findAll() {
        return quotationRepository.findAll();
    }

    public void remove(Quotation quotation) {
        quotationRepository.delete(quotation);
    }

    public Optional<Quotation> findById(final long id) {
        return quotationRepository.findById(id);
    }

    public void modify(final Quotation quotation, final String authorName, String content) {
        quotation.setAuthorName(authorName);
        quotation.setContent(content);

        quotationRepository.save(quotation);
    }

    public Quotation write(final String authorName, final String content) {
        final Quotation quotation = new Quotation(authorName, content);

        quotationRepository.save(quotation);

        return quotation;
    }
}
