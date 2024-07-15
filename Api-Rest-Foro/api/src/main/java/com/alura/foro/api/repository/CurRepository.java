package com.alura.foro.api.repository;

import com.alura.foro.api.modelo.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurRepository extends JpaRepository<Subject, Long> {
}
