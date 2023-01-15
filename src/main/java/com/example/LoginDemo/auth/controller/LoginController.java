package com.example.LoginDemo.auth.controller;


import com.example.LoginDemo.auth.entities.LoginDTO;
import com.example.LoginDemo.auth.entities.LoginRTO;
import com.example.LoginDemo.auth.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public LoginRTO login(@RequestBody LoginDTO loginDTO) throws Exception {
        LoginRTO loginRTO = loginService.login(loginDTO);
        if (loginRTO == null) throw new Exception("E' occorso un errore");
        return loginRTO;
    }
}
