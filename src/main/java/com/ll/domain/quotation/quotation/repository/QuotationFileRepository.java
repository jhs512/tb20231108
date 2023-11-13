package com.ll.domain.quotation.quotation.repository;

import com.ll.domain.quotation.quotation.entity.Quotation;
import com.ll.standard.util.Ut;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class QuotationFileRepository implements QuotationRepository {
    public static final String QUOTATION_DATA_PATH = "data/prod/quotation/";
    private static final String LAST_ID_FILE_PATH = QUOTATION_DATA_PATH + "lastId.txt";

    @Override
    @SneakyThrows
    public List<Quotation> findAll() {
        return Files.walk(Path.of(QUOTATION_DATA_PATH))
                .filter(Files::isRegularFile) // 첫 번째 필터: 레귤러 파일인지 확인
                .filter(path -> path.getFileName().toString().matches("\\d+\\.json")) // 두 번째 필터: 파일 이름이 정수와 .json 확장자를 가지는지 확인
                .map(path -> Ut.file.getContent(path.toString(), Quotation.class))
                .toList();
    }

    @Override
    public boolean delete(final Quotation quotation) {
        return Ut.file.delete(_getQuotationFilePath(quotation));
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
