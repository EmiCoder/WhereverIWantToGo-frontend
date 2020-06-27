package com.example.frontend.service;

import com.example.frontend.domain.Mail;
import com.example.frontend.domain.User;


public class EmailSendingService {

    public static void sendEmail(User user) throws Exception {
        Mail email = new Mail(user);
        email.getEmail().send();
    }
}
