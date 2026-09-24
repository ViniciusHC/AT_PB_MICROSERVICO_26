package com.TP3.forum_service.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long idUsuario;
    String titulo;
    String titutloJogo;
    String texto;
    LocalDateTime dataCriacao;
    TopicoStatus status;
    Long idJogo;
    String motivoErro;
}
