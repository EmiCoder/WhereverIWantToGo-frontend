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
@Table(name= "USER_REQUESTS")
public class UserRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;
    @Column(name = "TEMPERATURE")
    private String temperature;
    @Column(name = "MTH")
    private String month;
    @Column(name = "COUNTRY")
    private String countryName;
}
