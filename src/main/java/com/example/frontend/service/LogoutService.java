package com.example.frontend.service;

import com.example.frontend.database.STM;
import com.example.frontend.domain.DataSettings;

import java.sql.SQLException;
import java.sql.Statement;

public class LogoutService {

    public static void saveLogout(int userId) throws SQLException {
        Statement statement = STM.getStatement();
        statement.execute("insert into logouts (LOGOUT_DATE, LOGOUT_TIME, USER_ID) values (" +
                "'" + DataSettings.setDate() + "'" + ", " +
                "'" + DataSettings.setTime() + "'" + "," + userId + ")");
        statement.close();
    }
}
