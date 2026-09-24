package com.TP2.PJB.repository;
import com.TP2.PJB.model.Boardgame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BoardGameRepository extends JpaRepository<Boardgame,Long> {
    Optional<Boardgame> findById(Long id);
    Optional<Boardgame> findByNome(String nome);
}

