package com.br.biblioteca.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocatarioDTO {

    private Long id;
    private String nome;
    private String sexo;
    private String telefone;
    private String email;
    private LocalDate dataNascimento;
    private String cpf;
    private List<Long> alugueisIds;
}
