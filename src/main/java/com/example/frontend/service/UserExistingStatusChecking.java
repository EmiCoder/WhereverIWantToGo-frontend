package com.example.frontend.service;

import com.example.frontend.database.STM;
import com.example.frontend.domain.ForgottenPasswordUser;
import com.example.frontend.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserExistingStatusChecking {
    public static boolean isUserExisting(User user) throws SQLException {

        Statement statement = STM.getStatement();
        ResultSet resultSet = statement.executeQuery("select * from users where NICK = " +  "'" + user.getNick() + "'" +
                                                                                    " and FIRSTNAME = " + "'" + user.getFirstname() + "'" +
                                                                                    " and LASTNAME = " + "'" + user.getLastname() + "'" +
                                                                                    " and AGE = " + user.getAge() +
                                                                                    " and EMAIL = " + "'" + user.getEMail() + "'");
        resultSet.next();
        boolean result = resultSet.isFirst();
        statement.close();

        return result;
    }
}
