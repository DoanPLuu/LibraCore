package com.libracoreteam.libracore.util;

import java.sql.Connection;
import java.sql.SQLException;

public final class TestDB {

    private TestDB() {}

    public static void testdb() throws SQLException {
        try (Connection connection = DBConnection.getConnection()) {
            if (!connection.isValid(2)) {
                throw new SQLException("Database connection is not valid.");
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        testdb();
        System.out.println("Database connection successful.");
    }
}
