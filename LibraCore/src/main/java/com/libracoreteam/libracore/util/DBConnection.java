/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.libracoreteam.libracore.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;


/**
 *
 * @author luuis
 */
public class DBConnection {
    private static final HikariDataSource ds;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(ConfigUtility.getConfig("db.url"));
        config.setUsername(ConfigUtility.getConfig("db.user"));
        config.setPassword(ConfigUtility.getConfig("db.password"));
        config.setMaximumPoolSize(10);

        ds = new HikariDataSource(config);
    }

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
