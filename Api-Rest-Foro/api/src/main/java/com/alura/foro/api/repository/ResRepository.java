package com.alura.foro.api.repository;

import com.alura.foro.api.modelo.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResRepository extends JpaRepository<Answer, Long> {
}
