package com.br.biblioteca.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutorDTO {

    private Long id;
    private String nome;
    private String sexo;
    private Integer anoNascimento;
    private String cpf;
    private List<Long> livrosIds;
}
