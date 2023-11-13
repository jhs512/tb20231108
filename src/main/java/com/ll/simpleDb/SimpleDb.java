package com.ll.simpleDb;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SimpleDb {
    private final Connection connection;
    private boolean devMode;

    @SneakyThrows
    public SimpleDb(final String host, final String username, final String password, final String dbName) {
        String url = "jdbc:mysql://" + host + "/" + dbName;
        this.connection = DriverManager.getConnection(url, username, password);
        this.devMode = false;
    }

    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }

    @SneakyThrows
    public void run(String sql, Object... params) {
        @Cleanup PreparedStatement stmt = connection.prepareStatement(sql);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        stmt.execute();

        if (devMode) {
            System.out.println("Executed SQL: " + sql);
            for (Object param : params) {
                System.out.println("With param: " + param);
            }
        }
    }

    @SneakyThrows
    public void close() {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public Sql genSql() {
        return new Sql(connection);
    }
}