package com.example.frontend.service;

import com.example.frontend.database.DbManager;
import com.example.frontend.database.STM;
import com.example.frontend.domain.DataSettings;
import com.example.frontend.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterSavingService {

    private static int userId;

    public static void addRegisterToDatabase(User user) throws SQLException {
        Statement statement = STM.getStatement();
        ResultSet resultSet = statement.executeQuery("select * from users where NICK = " +  "'" + user.getNick() + "'" + " and EMAIL = " + "'" + user.getEMail() + "'");
        resultSet.next();
        if (resultSet.isLast()) {
            userId = Integer.parseInt(resultSet.getString(1));
        }

        statement.execute("insert into registers (USER_ID, REGISTER_DATE, REGISTER_TIME) values " +
                "(" + userId +","  +
                "'" + DataSettings.setDate() + "'" + ", " +
                "'" + DataSettings.setTime() + "'" +")");
        statement.close();
    }
}
