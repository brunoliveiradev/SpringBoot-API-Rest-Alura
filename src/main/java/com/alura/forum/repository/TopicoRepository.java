package com.alura.forum.repository;

import com.alura.forum.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
}
