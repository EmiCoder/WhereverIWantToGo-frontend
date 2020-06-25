package com.example.frontend.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbManager {

    private Connection conn;
    private static DbManager dbManagerInstance;

    private DbManager() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", "emicoder");
        connectionProps.put("password", "emicoder_password");
        conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/wherever_i_want?serverTimezone=Europe/Warsaw&useSSL=False&allowPublicKeyRetrieval=true",
                connectionProps);
    }

    public static DbManager getInstance() throws SQLException {
        if (dbManagerInstance == null) {
            dbManagerInstance = new DbManager();
        }
        return dbManagerInstance;
    }

    public Connection getConnection() {
        return conn;
    }
}
