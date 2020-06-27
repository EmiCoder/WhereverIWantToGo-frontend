package com.example.frontend.domain;

import lombok.Getter;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.sql.SQLException;

@Getter
public class MailForgottenPassword {

    private HtmlEmail email = new HtmlEmail();

    public MailForgottenPassword(ForgottenPasswordUser user, StringBuilder password) throws EmailException {
        this.email.setHostName("smtp.gmail.com");
        this.email.setSmtpPort(465);
        this.email.setSSLOnConnect(true);
        this.email.setFrom("kkodilla@gmail.com");
        this.email.setAuthentication("kkodilla@gmail.com", "rhthblpjqlumdikr");
        this.email.addTo(user.getEMail().toString());
        this.email.setSubject("Password to WhereverIWantToGo App");
        this.email.setTextMsg("Hello " + user.getNick() + "!" + "\n" +
                "With plesure we remind your uniqe password to app: " + "\n" +
                password + "\n" +
                "Thank you for using our app!");
    }
}
