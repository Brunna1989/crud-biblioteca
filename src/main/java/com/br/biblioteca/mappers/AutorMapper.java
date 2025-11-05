package com.br.biblioteca.mappers;

import com.br.biblioteca.dtos.AutorDTO;
import com.br.biblioteca.entities.Autor;
import com.br.biblioteca.entities.Livro;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AutorMapper {

    public AutorDTO toDto(Autor autor) {
        if (autor == null) return null;
        List<Long> livrosIds = autor.getLivros() == null
                ? Collections.emptyList()
                : autor.getLivros().stream()
                .map(Livro::getId)
                .collect(Collectors.toList());

        return AutorDTO.builder()
                .id(autor.getId())
                .nome(autor.getNome())
                .sexo(autor.getSexo())
                .anoNascimento(autor.getAnoNascimento())
                .cpf(autor.getCpf())
                .livrosIds(livrosIds)
                .build();
    }

    public AutorDTO toDtoShallow(Autor autor) {
        if (autor == null) return null;
        return AutorDTO.builder()
                .id(autor.getId())
                .nome(autor.getNome())
                .sexo(autor.getSexo())
                .anoNascimento(autor.getAnoNascimento())
                .cpf(autor.getCpf())
                .livrosIds(null)
                .build();
    }

    public Autor toEntity(AutorDTO dto) {
        if (dto == null) return null;
        return Autor.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .sexo(dto.getSexo())
                .anoNascimento(dto.getAnoNascimento())
                .cpf(dto.getCpf())
                .build();
    }
}
