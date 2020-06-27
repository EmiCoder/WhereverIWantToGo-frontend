package com.example.frontend.domain;

import com.example.frontend.database.DbManager;
import lombok.Getter;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Getter
public class Mail {

    private HtmlEmail email = new HtmlEmail();

    public Mail(User user) throws EmailException, SQLException {
        this.email.setHostName("smtp.gmail.com");
        this.email.setSmtpPort(465);
        this.email.setSSLOnConnect(true);
        this.email.setFrom("kkodilla@gmail.com");
        this.email.setAuthentication("kkodilla@gmail.com", "rhthblpjqlumdikr");
        this.email.addTo(user.getEMail().toString());
        this.email.setSubject("Password to WhereverIWantToGo App");
        this.email.setTextMsg("Hello " + user.getNick() + "!" + "\n" +
                "Your uniqe password to app is: " + "\n" +
                getUserPassword(user) + "\n" +
                "Thank you for using our app!");

    }

    private String getUserPassword(User user) throws SQLException {
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



