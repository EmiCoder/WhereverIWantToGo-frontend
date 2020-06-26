package com.example.frontend.service;

import com.example.frontend.database.DbManager;
import com.example.frontend.domain.ForgottenPasswordUser;
import com.example.frontend.domain.User;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.vaadin.flow.component.notification.Notification;

public class ForgotPasswordService {

    private static StringBuilder password;

    public static void getPassword(ForgottenPasswordUser forgottenUser) throws Exception {
        if (isExistTheUser(forgottenUser)) {
                EmailSendingForgottenPasswordService.sendEmail(forgottenUser, password);
        } else {
            Notification.show("Sorry. User with that data doesn't exist.");
        }
    }

    private static boolean isExistTheUser(ForgottenPasswordUser forgottenUser) throws SQLException {
        DbManager dbManager = DbManager.getInstance();
        Statement statement = dbManager.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users where NICK = " +  "'" + forgottenUser.getNick() + "'" +
                                                                                                " and FIRSTNAME = " + "'" + forgottenUser.getFirstname() + "'" +
                                                                                                " and LASTNAME = " + "'" + forgottenUser.getLastname() + "'" +
                                                                                                " and EMAIL = " + "'" + forgottenUser.getEMail() + "'" +
                                                                                                "and AGE = " + forgottenUser.getAge());
        resultSet.next();
        if (resultSet.isLast()) {
            password = new StringBuilder(resultSet.getString(7));
            statement.close();
            return true;
        }
        statement.close();
        return false;
    }
}
