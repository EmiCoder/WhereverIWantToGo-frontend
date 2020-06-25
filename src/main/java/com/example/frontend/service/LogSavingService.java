package com.example.frontend.service;

import com.example.frontend.database.DbManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogSavingService {

    public static void saveLog(int userId) throws SQLException {
        Statement statement = getStatement();
        getStatement().execute("insert into logs_in (USER_ID, LOGIN_DATE, LOGIN_TIME) values (" + userId + ", " +
                                                                                                        "'" + setDate() + "'" + ", " +
                                                                                                        "'" + setTime() + "'" +")");
        statement.close();
    }

    private static Statement getStatement() throws SQLException {
        DbManager dbManager = DbManager.getInstance();
        return dbManager.getConnection().createStatement();
    }

    private static String setDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(calendar.getTime());
    }

    private static String setTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(calendar.getTime());
    }
}
