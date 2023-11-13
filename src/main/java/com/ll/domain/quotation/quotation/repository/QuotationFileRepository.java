package com.ll.domain.quotation.quotation.repository;

import com.ll.domain.quotation.quotation.entity.Quotation;
import com.ll.standard.util.Ut;

import java.util.List;
import java.util.Optional;

public class QuotationFileRepository implements QuotationRepository {
    @Override
    public List<Quotation> findAll() {
        return null;
    }

    @Override
    public void delete(final Quotation quotation) {

    }

    @Override
    public Optional<Quotation> findById(final long id) {
        return Optional.empty();
    }

    @Override
    public void save(final Quotation quotation) {
        if (quotation.getId() == null) {
            quotation.setId(getLastId() + 1);
            setLastId(quotation.getId());
        }
    }

    private void setLastId(final long id) {
        Ut.file.save("data/prod/quotation/lastId.txt", id);
    }

    private long getLastId() {
        return Ut.file.getContentAsLong("data/prod/quotation/lastId.txt", 0);
    }
}
