package com.example.frontend.service;

import com.example.frontend.domain.ForgottenPasswordUser;
import com.example.frontend.domain.MailForgottenPassword;

public class EmailSendingForgottenPasswordService {

    public static void sendEmail(ForgottenPasswordUser user, StringBuilder password) throws Exception {
        MailForgottenPassword mail = new MailForgottenPassword(user, password);
        mail.getEmail().send();
    }
}
