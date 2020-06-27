package com.example.frontend.database;

import lombok.Getter;

import java.sql.SQLException;
import java.sql.Statement;

@Getter
public class STM {

    public static Statement getStatement() throws SQLException {
        DbManager dbManager = DbManager.getInstance();
        return dbManager.getConnection().createStatement();
    }
}
