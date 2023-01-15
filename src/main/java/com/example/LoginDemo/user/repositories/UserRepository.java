package com.example.LoginDemo.user.repositories;

import com.example.LoginDemo.user.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {


    Users findByEmail(String email);

    Users getByActivationCode(String activationCode);

    Users findByPasswordResetCode(String resetPasswordCode);
}
