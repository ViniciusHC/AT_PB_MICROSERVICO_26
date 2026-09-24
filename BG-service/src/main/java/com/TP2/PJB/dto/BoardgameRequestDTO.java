package com.TP2.PJB.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardgameRequestDTO {
    private String nome;
    private String descricao;
    private String editora;
    private String tipo;
    private String dataLancamento;
}
