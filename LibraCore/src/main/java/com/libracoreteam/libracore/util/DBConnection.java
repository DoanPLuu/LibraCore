package com.libracoreteam.libracore.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        String url = ConfigUtility.getConfig("db.url");
        String user = ConfigUtility.getConfig("db.user");
        String password = ConfigUtility.getConfig("db.password");
        return DriverManager.getConnection(url, user, password);
    }
}
