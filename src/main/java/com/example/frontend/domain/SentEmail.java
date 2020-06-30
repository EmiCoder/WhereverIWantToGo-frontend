package com.example.frontend.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "SENT_EMAILS_WITH_FORGOTTEN_PASSWORD")
public class SentEmail {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    @Column(name = "SENT_DATE")
    private String sentDate;
    @Column(name = "SENT_TIME")
    private String sentTime;
}
