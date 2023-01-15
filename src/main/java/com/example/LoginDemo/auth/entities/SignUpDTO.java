package com.example.LoginDemo.auth.entities;

import javax.persistence.Column;
import lombok.Data;

@Data
public class SignUpDTO {


    private String name;
    private String surname;
    private String email;
    private String password;
}
