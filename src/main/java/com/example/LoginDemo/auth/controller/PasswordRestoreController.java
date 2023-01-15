package com.example.LoginDemo.auth.controller;


import com.example.LoginDemo.auth.entities.RequestPasswordDTO;
import com.example.LoginDemo.auth.entities.RestorePasswordDTO;
import com.example.LoginDemo.auth.services.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/password")
public class PasswordRestoreController {

    @Autowired
    private PasswordService passwordService;

    @PostMapping("/request")
    public void passwordRequest(@RequestBody RequestPasswordDTO requestPasswordDTO) throws Exception {
       try {
           passwordService.request(requestPasswordDTO);
       } catch (Exception e){

       }
    }

    @PostMapping("/restore")
    public void passwordRestore(@RequestBody RestorePasswordDTO restorePasswordDTO) throws Exception {
        passwordService.restore(restorePasswordDTO);
    }

}
