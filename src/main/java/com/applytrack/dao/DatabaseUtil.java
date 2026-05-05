package com.applytrack.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


 public final class DatabaseUtil {

    private static final String DB_NAME = "applytrack";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME
            + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private DatabaseUtil() {
     }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("MySQL JDBC Driver not found: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static String getDatabaseName() {
        return DB_NAME;
    }
}
