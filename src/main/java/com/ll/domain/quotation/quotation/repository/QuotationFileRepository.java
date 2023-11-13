package com.ll.domain.quotation.quotation.repository;

import com.ll.domain.quotation.quotation.entity.Quotation;
import com.ll.standard.util.Ut;

import java.util.List;
import java.util.Optional;

public class QuotationFileRepository implements QuotationRepository {
    public static final String QUOTATION_DATA_PATH = "data/prod/quotation/";
    private static final String LAST_ID_FILE_PATH = QUOTATION_DATA_PATH + "lastId.txt";

    @Override
    public List<Quotation> findAll() {
        return null;
    }

    @Override
    public void delete(final Quotation quotation) {

    }

    @Override
    public Optional<Quotation> findById(final long id) {
        final String filePath = _getQuotationFilePath(id);

        return Optional.ofNullable(Ut.file.getContent(filePath, Quotation.class));
    }

    @Override
    public void save(final Quotation quotation) {
        if (quotation.getId() == null) {
            quotation.setId(getLastId() + 1);
            setLastId(quotation.getId());
        }

        Ut.file.save(_getQuotationFilePath(quotation), quotation);
    }

    private void setLastId(final long id) {
        Ut.file.save(LAST_ID_FILE_PATH, id);
    }

    private long getLastId() {
        return Ut.file.getContentAsLong(LAST_ID_FILE_PATH, 0);
    }

    public String _getQuotationFilePath(final Quotation quotation) {
        return _getQuotationFilePath(quotation.getId());
    }

    public String _getQuotationFilePath(final long id) {
        return QUOTATION_DATA_PATH + id + ".json";
    }
}
