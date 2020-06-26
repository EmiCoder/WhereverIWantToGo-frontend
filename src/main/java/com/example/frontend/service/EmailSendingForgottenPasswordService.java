package com.example.frontend.service;

import com.example.frontend.domain.ForgottenPasswordUser;
import org.apache.commons.mail.HtmlEmail;

public class EmailSendingForgottenPasswordService {

    public static void sendEmail(ForgottenPasswordUser user, StringBuilder password) throws Exception {
        HtmlEmail email = new HtmlEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(465);
        email.setSSLOnConnect(true);
        email.setFrom("kkodilla@gmail.com");
        email.setAuthentication("kkodilla@gmail.com", "rhthblpjqlumdikr");
        email.addTo(user.getEMail().toString());
        email.setSubject("Password to WhereverIWantToGo App");
        email.setTextMsg("Hello " + user.getNick() + "!" + "\n" +
                "With plesure we remind your uniqe password to app: " + "\n" +
                password + "\n" +
                "Thank you for using our app!");
        email.send();
    }
}
