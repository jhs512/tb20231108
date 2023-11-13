package com.ll.simpleDb;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class SimpleDb {
    private final Connection connection;
    private boolean devMode;

    @SneakyThrows
    public SimpleDb(final String host, final String username, final String password, final String dbName) {
        String url = "jdbc:mysql://" + host + "/" + dbName;
        this.connection = DriverManager.getConnection(url, username, password);
        this.devMode = false;
    }

    public Sql genSql() {
        return new Sql(connection, devMode);
    }

    public void run(String sql, Object... params) {
        Sql sqlObj = genSql();
        sqlObj.append(sql, params);
        sqlObj.update();
    }

    @SneakyThrows
    public void close() {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }
}