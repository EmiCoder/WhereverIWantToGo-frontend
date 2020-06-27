package com.example.frontend.service;

import com.example.frontend.database.DbManager;
import com.example.frontend.database.STM;
import com.example.frontend.domain.DataSettings;

import java.sql.SQLException;
import java.sql.Statement;

public class LogSavingService {

    public static void saveLog(int userId) throws SQLException {
        Statement statement = STM.getStatement();
        statement.execute("insert into logs_in (USER_ID, LOGIN_DATE, LOGIN_TIME) values (" + userId + ", " +
                                                                                                        "'" + DataSettings.setDate() + "'" + ", " +
                                                                                                        "'" + DataSettings.setTime() + "'" +")");
        statement.close();
    }
}
