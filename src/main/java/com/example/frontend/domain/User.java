package com.example.frontend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;
    @Column(name = "NICK")
    private StringBuilder nick;
    @Column(name = "FIRSTNAME")
    private StringBuilder firstname;
    @Column(name = "LASTNAME")
    private StringBuilder lastname;
    @Column(name = "AGE")
    private int age;
    @Column(name = "EMAIL")
    private StringBuilder eMail;
    @Column(name = "EMAIL_PASSWORD")
    private StringBuilder password;

    @OneToMany(
            targetEntity = LogIn.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<LogIn> logsIn = new ArrayList<>();

    @OneToMany(
            targetEntity = Request.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Request> requests = new ArrayList<>();

    @OneToMany(
            targetEntity = LogOut.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<LogOut> logsout = new ArrayList<>();

    @OneToMany(
            targetEntity = SentEmail.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<SentEmail> sentEmails = new ArrayList<>();
}
