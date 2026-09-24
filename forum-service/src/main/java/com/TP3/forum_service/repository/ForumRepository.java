package com.TP3.forum_service.repository;

import com.TP3.forum_service.entities.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ForumRepository extends JpaRepository<Topico, Long> {
    Optional<Topico> findById(Long id);
}
