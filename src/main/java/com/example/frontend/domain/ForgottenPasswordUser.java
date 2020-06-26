package com.example.frontend.domain;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ForgottenPasswordUser {
    private StringBuilder nick;
    private StringBuilder firstname;
    private StringBuilder lastname;
    private int age;
    private StringBuilder eMail;
}
