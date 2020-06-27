package com.example.frontend.service;

import com.example.frontend.database.STM;
import com.example.frontend.domain.DataSettings;

import java.sql.SQLException;
import java.sql.Statement;

public class LogSavingService {

    public static void saveLog(int userId) throws SQLException {
        Statement statement = STM.getStatement();
        statement.execute("insert into logs_in (LOGIN_DATE, LOGIN_TIME, USER_ID) values (" +
                                                                                                "'" + DataSettings.setDate() + "'" + ", " +
                                                                                                  "'" + DataSettings.setTime() + "'" + "," + userId + ")");
        statement.close();
    }
}
