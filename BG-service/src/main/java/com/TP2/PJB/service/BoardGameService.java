package com.TP2.PJB.service;
import com.TP2.PJB.dto.BoardgameRequestDTO;
import com.TP2.PJB.model.Boardgame;
import com.TP2.PJB.repository.BoardGameRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardGameService {

    private final BoardGameRepository repository;

    public BoardGameService(BoardGameRepository repository) {
        this.repository = repository;
    }

    public List<Boardgame> listarJogos(){
        return repository.findAll();
    }

    public Boardgame listarJogo(Long id){
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Jogo não encontrado"));
    }

    public Boardgame adicionarJogo(BoardgameRequestDTO jogo){
        Boardgame boardgame = new Boardgame();
        boardgame.setNome(jogo.getNome());
        boardgame.setDescricao(jogo.getDescricao());
        boardgame.setTipo(jogo.getTipo());
        boardgame.setEditora(jogo.getEditora());
        boardgame.setDataLancamento(jogo.getDataLancamento());
        return repository.save(boardgame);
    }

    public Boardgame alterarJogo(Long id, BoardgameRequestDTO jogoAlterado){
        Boardgame jogo = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Jogo não encontrado"));
        jogo.setNome(jogoAlterado.getNome());
        jogo.setDescricao(jogoAlterado.getDescricao());
        jogo.setTipo(jogoAlterado.getTipo());
        jogo.setEditora(jogoAlterado.getEditora());
        jogo.setDataLancamento(jogoAlterado.getDataLancamento());
        return repository.save(jogo);
    }

    public Boardgame buscarJogoPorTitulo (String nome){
        return repository.findByNome(nome).orElseThrow(() -> new EntityNotFoundException("Jogo não encontrado"));
    }

    public void removerJogo(Long id) {
        Boardgame jogo = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Jogo não encontrado"));
        repository.delete(jogo);
    }

}
