package com.example.frontend.service;

import com.example.frontend.database.STM;
import com.example.frontend.domain.User;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.SQLException;
import java.sql.Statement;

public class UserSavingService {

    public static void addUserToDatabase(User user) throws SQLException {
        Statement statement = STM.getStatement();
        statement.execute("insert into users (NICK, FIRSTNAME, LASTNAME, AGE, EMAIL, EMAIL_PASSWORD) values " +
                                                                                            "(" + "'" + user.getNick() + "'" + ","  +
                                                                                            "'" + user.getFirstname() + "'" + "," +
                                                                                            "'" + user.getLastname() + "'" + "," +
                                                                                            user.getAge() + "," +
                                                                                            "'" + user.getEMail() + "'" + "," +
                                                                                            "'" + generatePassword() + "'" + ")");
        statement.close();
    }

    private static String generatePassword() {
        return RandomStringUtils.random(10, true, true);
    }
}
