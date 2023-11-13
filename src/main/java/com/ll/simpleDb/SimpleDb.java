package com.ll.simpleDb;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

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
    public void run(String sql) {
        @Cleanup Statement stmt = connection.createStatement();
        stmt.execute(sql);

        if (devMode) {
            System.out.println("Executed SQL: " + sql);
        }
    }

    @SneakyThrows
    public void close() {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}