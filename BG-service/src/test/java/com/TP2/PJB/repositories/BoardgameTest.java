package com.TP2.PJB.repositories;
import com.TP2.PJB.model.Boardgame;
import com.TP2.PJB.repository.BoardGameRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BoardgameTest {

    @Autowired
    private BoardGameRepository boardGameRepository;

    @Test
    void deveSalvarOJogoComSucesso(){
        Boardgame boardgame = new Boardgame(null, "Teste", "Teste", "Teste", "Teste", "Teste");
        Boardgame savedBoardgame = boardGameRepository.save(boardgame);
        assertNotNull(savedBoardgame.getId());
    }

    @Test
    void deveBuscarJogoPorIdComSucesso() {
        Boardgame boardgame = boardGameRepository.save(
                new Boardgame(null, "Catan", "desc", "Devir", "Euro", "1995-01-01"));

        Optional<Boardgame> encontrado = boardGameRepository.findById(boardgame.getId());

        assertTrue(encontrado.isPresent());
        assertEquals("Catan", encontrado.get().getNome());
    }

    @Test
    void deveAtualizarOJogoComSucesso() {
        Boardgame boardgame = boardGameRepository.save(
                new Boardgame(null, "Catan", "desc", "Devir", "Euro", "1995-01-01"));

        boardgame.setNome("Catan Expansão");
        Boardgame atualizado = boardGameRepository.save(boardgame);

        Optional<Boardgame> reconsultado = boardGameRepository.findById(atualizado.getId());
        assertEquals("Catan Expansão", reconsultado.get().getNome());
    }

    @Test
    void deveRetornarVazioQuandoIdNaoExiste() {
        Optional<Boardgame> resultado = boardGameRepository.findById(999L);
        assertFalse(resultado.isPresent());
    }

    @Test
    void deveDeletarOJogoComSucesso(){
        Boardgame boardgame = new Boardgame(null, "Teste", "Teste", "Teste", "Teste", "Teste");
        boardGameRepository.save(boardgame);
        boardGameRepository.deleteById(boardgame.getId());
        Optional <Boardgame> jogoBuscado = boardGameRepository.findById(boardgame.getId());
        assertFalse(jogoBuscado.isPresent());
    }

}
