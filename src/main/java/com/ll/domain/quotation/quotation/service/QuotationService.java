package com.ll.domain.quotation.quotation.service;

import com.ll.domain.quotation.quotation.entity.Quotation;
import com.ll.domain.quotation.quotation.repository.QuotationRepository;

import java.util.List;
import java.util.Optional;

public class QuotationService {
    private final QuotationRepository quotationRepository;

    public QuotationService() {
        quotationRepository = new QuotationRepository();
    }

    public List<Quotation> findAll() {
        return quotationRepository.findAll();
    }

    public void remove(Quotation quotation) {
        quotationRepository.delete(quotation);
    }

    public Optional<Quotation> findById(long id) {
        return quotationRepository.findById(id);
    }

    public void modify(Quotation quotation, String authorName, String content) {
        quotation.setAuthorName(authorName);
        quotation.setContent(content);

        quotationRepository.save(quotation);
    }

    public Quotation write(String authorName, String content) {
        final Quotation quotation = new Quotation(authorName, content);

        quotationRepository.save(quotation);

        return quotation;
    }
}
