package com.example.LoginDemo.auth.services;


import com.example.LoginDemo.auth.entities.SignUpActivationDTO;
import com.example.LoginDemo.auth.entities.SignUpDTO;
import com.example.LoginDemo.notifications.services.MailNotificationService;
import com.example.LoginDemo.user.entities.Users;
import com.example.LoginDemo.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Service
public class SignUpService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailNotificationService mailNotificationService;


    public Users signup(@RequestBody SignUpDTO signUpDTO) throws Exception {
        Users userInDb = userRepository.findByEmail(signUpDTO.getEmail());
        if (userInDb != null) throw new Exception("L'utente esiste già");
        Users user = new Users();
        user.setName(signUpDTO.getName());
        user.setSurname(signUpDTO.getSurname());
        user.setEmail(signUpDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        user.setActive(false);
        user.setActivationCode(UUID.randomUUID().toString());

        mailNotificationService.sendActivationEmail(user);

        return userRepository.save(user);
    }

    public Users activate(SignUpActivationDTO signUpActivationDTO) throws Exception {
        Users user = userRepository.getByActivationCode(signUpActivationDTO.getActivationCode());
        if (user == null) throw new Exception("L'utente non esiste");
        user.setActive(true);
        user.setActivationCode(null);
        return userRepository.save(user);
    }
}
