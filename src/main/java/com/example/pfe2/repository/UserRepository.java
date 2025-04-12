package com.example.pfe2.repository;

import com.example.pfe2.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.pfe2.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByRole(Role role);

    Optional<User> findByEmail(String email);

    Optional<User> findByVerificationCode(String verificationCode);
    List<User> findByUsernameNot(String username);
}

