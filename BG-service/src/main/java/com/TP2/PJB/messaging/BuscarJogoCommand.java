package com.TP2.PJB.messaging;
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
