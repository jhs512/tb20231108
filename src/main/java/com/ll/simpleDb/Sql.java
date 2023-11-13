package com.ll.simpleDb;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class Sql {
    private final Connection connection;
    private final StringBuilder sqlBuilder = new StringBuilder();
    private Object[] params = new Object[0];

    public Sql append(String sqlPart, Object... params) {
        if (sqlBuilder.length() > 0) {
            sqlBuilder.append(" ");
        }
        sqlBuilder.append(sqlPart);
        if (params.length > 0) {
            this.params = concatenate(this.params, params);
        }
        return this;
    }

    @SneakyThrows
    public long insert() {
        @Cleanup PreparedStatement stmt = connection.prepareStatement(sqlBuilder.toString(), Statement.RETURN_GENERATED_KEYS);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        stmt.executeUpdate();
        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
    }

    private Object[] concatenate(Object[] a, Object[] b) {
        int aLen = a.length;
        int bLen = b.length;
        Object[] c = new Object[aLen + bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

    @SneakyThrows
    public long update() {
        @Cleanup PreparedStatement stmt = connection.prepareStatement(sqlBuilder.toString());

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        return stmt.executeUpdate();
    }

    @SneakyThrows
    public long delete() {
        @Cleanup PreparedStatement stmt = connection.prepareStatement(sqlBuilder.toString());

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        return stmt.executeUpdate();
    }

    @SneakyThrows
    public Map<String, Object> selectRow() {
        @Cleanup PreparedStatement stmt = connection.prepareStatement(sqlBuilder.toString());

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        @Cleanup ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return resultSetToMap(rs);
        }

        return null;
    }

    private Map<String, Object> resultSetToMap(ResultSet rs) throws java.sql.SQLException {
        Map<String, Object> row = new HashMap<>();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            Object value = rs.getObject(i);
            row.put(columnName, value);
        }

        return row;
    }

    @SneakyThrows
    public <T> T selectRow(Class<T> cls) {
        @Cleanup PreparedStatement stmt = connection.prepareStatement(sqlBuilder.toString());

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        @Cleanup ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return resultSetToEntity(rs, cls);
        }

        return null;
    }

    @SneakyThrows
    public <T> List<T> selectRows(Class<T> cls) {
        @Cleanup PreparedStatement stmt = connection.prepareStatement(sqlBuilder.toString());

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        @Cleanup ResultSet rs = stmt.executeQuery();

        List<T> results = new ArrayList<>();
        while (rs.next()) {
            results.add(resultSetToEntity(rs, cls));
        }

        return results;
    }

    @SneakyThrows
    private <T> T resultSetToEntity(ResultSet rs, Class<T> cls) {
        T entity = cls.newInstance();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            Object value = rs.getObject(i);
            Field field = cls.getDeclaredField(columnName);
            field.setAccessible(true);
            field.set(entity, value);
        }

        return entity;
    }
}
