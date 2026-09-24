package com.TP2.PJB.controller;
import com.TP2.PJB.dto.BoardgameRequestDTO;
import com.TP2.PJB.model.Boardgame;
import com.TP2.PJB.service.BoardGameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boardgames")

public class BoardGameController {

    private final BoardGameService service;

    public BoardGameController(BoardGameService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> listarJogos(){
        return ResponseEntity.ok(service.listarJogos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listarJogos(@PathVariable Long id){
        return ResponseEntity.ok(service.listarJogo(id));
    }

    @PostMapping()
    public ResponseEntity<Object> adicionarJogos(@RequestBody BoardgameRequestDTO jogo){
        return ResponseEntity.ok(service.adicionarJogo(jogo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alterarJogos(@PathVariable Long id, @RequestBody BoardgameRequestDTO jogo){
        return ResponseEntity.ok(service.alterarJogo(id,jogo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluirJogos(@PathVariable Long id){
        service.removerJogo(id);
        return ResponseEntity.noContent().build();
    }

}
