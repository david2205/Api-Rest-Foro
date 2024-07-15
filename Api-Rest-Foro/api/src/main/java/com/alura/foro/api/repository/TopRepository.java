package com.alura.foro.api.repository;

import com.alura.foro.api.modelo.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopRepository extends JpaRepository<Type, Long> {
}
