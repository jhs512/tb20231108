package com.ll.simpleDb;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.sql.*;

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
}
