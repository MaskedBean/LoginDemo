package com.example.LoginDemo.auth.entities;

import com.example.LoginDemo.user.entities.Users;
import lombok.Data;

@Data
public class LoginRTO {

    private Users user;

    private String JWT;
}
