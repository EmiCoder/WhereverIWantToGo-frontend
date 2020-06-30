package com.example.frontend.service;


import com.example.frontend.database.STM;
import com.example.frontend.domain.DataSettings;
import com.example.frontend.domain.ForgottenPasswordUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SentEmailForgottenPasswordService {

    public static void saveSentEmail(ForgottenPasswordUser user) throws SQLException {
        Statement statement = STM.getStatement();
        int userId = getUserId(user);
        statement.execute("insert into SENT_EMAILS (SENT_DATE, SENT_TIME, USER_ID) values (" +
                                                                                        "'" + DataSettings.setDate() + "'" + ", " +
                                                                                        "'" + DataSettings.setTime() + "'" + "," + userId + ")");
        statement.close();
    }

    private static int getUserId(ForgottenPasswordUser user) throws SQLException {
        Statement statement = STM.getStatement();
        ResultSet resultSet = statement.executeQuery("select * from users where NICK = " +  "'" + user.getNick() + "'" +
                                                                                " and FIRSTNAME = " + "'" + user.getFirstname() + "'" +
                                                                                " and LASTNAME = " + "'" + user.getLastname() + "'" +
                                                                                " and AGE = " + user.getAge() +
                                                                                " and EMAIL = " + "'" + user.getEMail() + "'");
        resultSet.next();
        int userId = Integer.parseInt(resultSet.getString(1));
        statement.close();
        return userId;
    }
}
