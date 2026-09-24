package com.TP3.forum_service.controller;

import com.TP3.forum_service.DTO.TopicoDTO;
import com.TP3.forum_service.service.ForumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class ForumController {

    private final ForumService service;

    public ForumController(ForumService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> listarTopicos() {
        return ResponseEntity.ok(service.listarTopicos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listarTopico(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarTopico(id));
    }

    @PostMapping
    public ResponseEntity<Object> adicionarTopico(@RequestBody TopicoDTO topicoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.adicionarTopico(topicoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alterarTopico(@PathVariable Long id, @RequestBody TopicoDTO topicoDTO) {
        return ResponseEntity.ok(service.alterarTopico(id, topicoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removerTopico(@PathVariable Long id) {
        service.removerTopico(id);
        return ResponseEntity.noContent().build();
    }
}
