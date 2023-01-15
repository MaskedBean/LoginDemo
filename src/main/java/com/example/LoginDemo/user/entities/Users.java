package com.example.LoginDemo.user.entities;


import javax.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Table
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String name;
    private String surname;

    private boolean isActive;

    @Column(length = 36)
    private String activationCode;

    @Column(length = 36)
    private String passwordResetCode;

    @Column(unique = true)
    private String email;

    private String password;

    private LocalDateTime jwtCreatedOn;

}
