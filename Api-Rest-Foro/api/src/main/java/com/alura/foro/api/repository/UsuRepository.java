package com.alura.foro.api.repository;

import com.alura.foro.api.modelo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuRepository extends JpaRepository<User, Long> {
    UserDetails findByEmail(String username);
}
