package com.example.frontend.service;


import com.example.frontend.database.DbManager;
import com.example.frontend.domain.User;
import org.apache.commons.mail.HtmlEmail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class EmailSendingService {

    public static void sendEmail(User user) throws Exception {
        HtmlEmail email = new HtmlEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        email.setSSLOnConnect(true);
        email.setFrom("kkodilla@gmail.com");
        email.setAuthentication("kkodilla@gmail.com", "rhthblpjqlumdikr");
        email.addTo(user.getEMail().toString());
        email.setSubject("Password to WhereverIWantToGo App");
        email.setTextMsg("Hello " + user.getNick() + "!" + "\n" +
                        "Your uniqe password to app is: " + "\n" +
                        getUserPassword(user) + "\n" +
                        "Thank you for using our app!");
        email.send();
    }

    private static String getUserPassword(User user) throws SQLException {
        DbManager dbManager = DbManager.getInstance();
        Statement statement = dbManager.getConnection().createStatement();

        ResultSet resultSet = statement.executeQuery("select * from users where NICK = " +  "'" + user.getNick() + "'" +
                                                            " and FIRSTNAME = " + "'" + user.getFirstname() + "'" +
                                                            " and LASTNAME = " + "'" + user.getLastname() + "'" +
                                                            " and EMAIL = " + "'" + user.getEMail() + "'");
        resultSet.next();
        if (resultSet.isLast()) {
            String password = resultSet.getString(7);
            statement.close();
            return password;
        }
        statement.close();
        return null;

    }
}
