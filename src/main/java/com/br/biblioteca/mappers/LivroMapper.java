package com.br.biblioteca.mappers;

import com.br.biblioteca.dtos.LivroDTO;
import com.br.biblioteca.dtos.AutorDTO;
import com.br.biblioteca.entities.Livro;
import com.br.biblioteca.entities.Autor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LivroMapper {

    private final AutorMapper autorMapper;

    public LivroMapper(AutorMapper autorMapper) {
        this.autorMapper = autorMapper;
    }

    public LivroDTO toDto(Livro livro) {
        if (livro == null) return null;

        List<AutorDTO> autores = livro.getAutores() == null
                ? Collections.emptyList()
                : livro.getAutores().stream()
                .map(autorMapper::toDtoShallow) // evita ciclos
                .collect(Collectors.toList());

        return LivroDTO.builder()
                .id(livro.getId())
                .nome(livro.getNome())
                .isbn(livro.getIsbn())
                .dataPublicacao(livro.getDataPublicacao())
                .autores(autores)
                .build();
    }

    public Livro toEntity(LivroDTO dto) {
        if (dto == null) return null;

        List<Autor> autores = dto.getAutores() == null
                ? null
                : dto.getAutores().stream()
                .map(aDto -> Autor.builder().id(aDto.getId()).build()) // apenas id para assoc.
                .collect(Collectors.toList());

        return Livro.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .isbn(dto.getIsbn())
                .dataPublicacao(dto.getDataPublicacao())
                .autores(autores)
                .build();
    }


    public LivroDTO toDtoShallow(Livro livro) {
        if (livro == null) return null;
        return LivroDTO.builder()
                .id(livro.getId())
                .nome(livro.getNome())
                .isbn(livro.getIsbn())
                .dataPublicacao(livro.getDataPublicacao())
                .autores(null)
                .build();
    }

    public Livro toEntityWithId(Long id) {
        if (id == null) return null;
        return Livro.builder().id(id).build();
    }
}
