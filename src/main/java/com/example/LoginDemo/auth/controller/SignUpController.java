package com.example.LoginDemo.auth.controller;

import com.example.LoginDemo.auth.entities.SignUpActivationDTO;
import com.example.LoginDemo.auth.entities.SignUpDTO;
import com.example.LoginDemo.auth.services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @PostMapping("/signup")
    public void signUp(@RequestBody SignUpDTO signUpDTO) throws Exception {
        signUpService.signup(signUpDTO);
    }

    @PostMapping("/signup/activation")
    public void activate(@RequestBody SignUpActivationDTO signUpActivationDTO) throws Exception {
        signUpService.activate(signUpActivationDTO);
    }
}
