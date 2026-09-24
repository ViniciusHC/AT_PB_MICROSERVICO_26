package com.TP3.forum_service.messaging;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuscarJogoCommand {

    private Long idTopico;
    private String tituloJogo;

}
