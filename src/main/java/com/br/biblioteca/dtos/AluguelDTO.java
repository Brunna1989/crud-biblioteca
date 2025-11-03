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
public class AluguelDTO {

    private Long id;
    private LocalDate dataRetirada;
    private LocalDate dataDevolucao;
    private Long locatarioId;
    private List<LivroDTO> livros;
}
