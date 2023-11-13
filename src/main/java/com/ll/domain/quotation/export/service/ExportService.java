package com.ll.domain.quotation.export.service;

import com.ll.domain.quotation.quotation.entity.Quotation;
import com.ll.domain.quotation.quotation.service.QuotationService;
import com.ll.standard.util.Ut;

import java.util.List;

public class ExportService {
    final QuotationService quotationService;

    public ExportService() {
        quotationService = new QuotationService();
    }

    public void export() {
        final List<Quotation> quotations = quotationService.findAll();
        Ut.file.save("data/data.json", quotations);
    }
}
