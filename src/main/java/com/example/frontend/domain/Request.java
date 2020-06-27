package com.example.frontend.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "REQUESTS")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
    @Column(name = "TEMPERATURE")
    private int temperature;
    @Column(name = "MTH")
    private String month;
    @Column(name = "COUNTRY")
    private String countryName;
    @Column(name = "REQUEST_DATE")
    private String requestDate;
}
