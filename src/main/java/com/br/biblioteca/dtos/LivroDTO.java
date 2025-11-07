package com.br.biblioteca.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class LivroDTO {
    private Long id;
    private String nome;
    private String isbn;
    private LocalDate dataPublicacao;
    private Boolean disponivel;
    private List<AutorDTO> autores;
    private List<Long> autoresIds;
}
