package com.ll.simpleDb;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class Sql {
    private final Connection connection;
    private final boolean devMode;
    private final StringBuilder sqlBuilder = new StringBuilder();
    private final List<Object> params = new ArrayList<>();

    public Sql append(String sql, Object... params) {
        sqlBuilder.append(sql).append(" ");
        if (params != null) {
            for (Object param : params) {
                this.params.add(param);
            }
        }
        return this;
    }

    public Sql appendIn(String sql, List<?> params) {
        String inClause = IntStream.range(0, params.size()).mapToObj(i -> "?").collect(Collectors.joining(", "));
        sqlBuilder.append(sql.replace("?", inClause)).append(" ");
        this.params.addAll(params);
        return this;
    }

    @SneakyThrows
    public long insert() {
        return executeUpdate(true);
    }

    @SneakyThrows
    public long update() {
        return executeUpdate(false);
    }

    @SneakyThrows
    public long delete() {
        return executeUpdate(false);
    }

    @SneakyThrows
    public long selectLong() {
        @Cleanup PreparedStatement stmt = prepareStatement();
        @Cleanup ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getLong(1);
        }
        return 0;
    }

    @SneakyThrows
    private <T> T resultSetToEntity(ResultSet rs, Class<T> cls) {
        T entity = cls.getDeclaredConstructor().newInstance();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            Object value = rs.getObject(i);
            Field field;
            try {
                field = cls.getDeclaredField(columnName);
            } catch (NoSuchFieldException e) {
                continue; // 클래스에 해당 필드가 없는 경우, 무시하고 계속합니다.
            }
            field.setAccessible(true);
            field.set(entity, value);
        }

        return entity;
    }

    @SneakyThrows
    public <T> T selectRow(Class<T> cls) {
        @Cleanup PreparedStatement stmt = prepareStatement();
        @Cleanup ResultSet rs = stmt.executeQuery();
        return rs.next() ? resultSetToEntity(rs, cls) : null;
    }

    @SneakyThrows
    public Map<String, Object> selectRow() {
        @Cleanup PreparedStatement stmt = prepareStatement();
        @Cleanup ResultSet rs = stmt.executeQuery();
        return rs.next() ? resultSetToMap(rs) : null;
    }

    // ... 기존의 다른 메서드들 ...

    private Map<String, Object> resultSetToMap(ResultSet rs) throws java.sql.SQLException {
        Map<String, Object> rowMap = new HashMap<>();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            rowMap.put(metaData.getColumnName(i), rs.getObject(i));
        }
        return rowMap;
    }

    @SneakyThrows
    public <T> List<T> selectRows(Class<T> cls) {
        @Cleanup PreparedStatement stmt = prepareStatement();
        @Cleanup ResultSet rs = stmt.executeQuery();
        List<T> result = new ArrayList<>();
        while (rs.next()) {
            result.add(resultSetToEntity(rs, cls));
        }
        return result;
    }

    private PreparedStatement prepareStatement() throws Exception {
        printSqlIfDevMode();
        PreparedStatement stmt = connection.prepareStatement(sqlBuilder.toString(), PreparedStatement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < params.size(); i++) {
            stmt.setObject(i + 1, params.get(i));
        }
        return stmt;
    }

    @SneakyThrows
    private long executeUpdate(boolean returnGeneratedKeys) {
        @Cleanup PreparedStatement stmt = prepareStatement();
        int affectedRows = stmt.executeUpdate();
        if (returnGeneratedKeys) {
            @Cleanup ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }
        }
        return affectedRows;
    }

    private void printSqlIfDevMode() {
        if (devMode) {
            System.out.println("== rawSql ==\n" + rawSql());
        }
    }

    private String rawSql() {
        StringBuilder processedSql = new StringBuilder(sqlBuilder);
        int lastIndex = 0;

        for (Object param : params) {
            lastIndex = processedSql.indexOf("?", lastIndex);
            if (lastIndex == -1) {
                // 더 이상 치환할 물음표가 없으면 반복 중단
                break;
            }

            String replacement;

            // 그 외의 경우, 단순 문자열로 변환
            if (param instanceof Boolean) {
                replacement = param.toString().toUpperCase();
            } else {
                replacement = "'" + param.toString() + "'";
            }

            processedSql.replace(lastIndex, lastIndex + 1, replacement);
            lastIndex += replacement.length();
        }

        return processedSql.toString();
    }
}