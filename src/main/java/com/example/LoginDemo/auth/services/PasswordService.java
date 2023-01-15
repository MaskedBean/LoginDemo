package com.example.LoginDemo.auth.services;

import com.example.LoginDemo.auth.entities.RequestPasswordDTO;
import com.example.LoginDemo.auth.entities.RestorePasswordDTO;
import com.example.LoginDemo.notifications.services.MailNotificationService;
import com.example.LoginDemo.user.entities.Users;
import com.example.LoginDemo.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Service
public class PasswordService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailNotificationService mailNotificationService;

    public void request(@RequestBody RequestPasswordDTO requestPasswordDTO) throws Exception {
        Users userFromDB = userRepository.findByEmail(requestPasswordDTO.getEmail());
        if (userFromDB == null) throw new Exception("User is null");
        userFromDB.setPasswordResetCode(UUID.randomUUID().toString());
        mailNotificationService.sendPasswordResetMail(userFromDB);
        userRepository.save(userFromDB);
    }


    public Users restore(@RequestBody RestorePasswordDTO restorePasswordDTO) throws Exception {
        Users userFromDB = userRepository.findByPasswordResetCode(restorePasswordDTO.getResetPasswordCode());
        if (userFromDB == null) throw new Exception("User is null");
        userFromDB.setPassword(passwordEncoder.encode(restorePasswordDTO.getNewPassword()));
        userFromDB.setPasswordResetCode(null);

        userFromDB.setActive(true);
        userFromDB.setActivationCode(null);

        return userRepository.save(userFromDB);

    }
}
