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
public class LivroDTO {

    private Long id;
    private String nome;
    private String isbn;
    private LocalDate dataPublicacao;

    private List<AutorDTO> autores;

    private List<Long> autoresIds;
}
