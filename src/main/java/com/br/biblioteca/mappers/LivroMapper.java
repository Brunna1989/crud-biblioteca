package com.br.biblioteca.mappers;

import com.br.biblioteca.dtos.LivroDTO;
import com.br.biblioteca.dtos.AutorDTO;
import com.br.biblioteca.entities.Livro;
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
                .map(autorMapper::toDtoShallow)
                .collect(Collectors.toList());

        return LivroDTO.builder()
                .id(livro.getId())
                .nome(livro.getNome())
                .isbn(livro.getIsbn())
                .dataPublicacao(livro.getDataPublicacao())
                .disponivel(livro.isDisponivel()) // ✅ agora o DTO reflete o estado real
                .autores(autores)
                .build();
    }

    public Livro toEntity(LivroDTO dto) {
        if (dto == null) return null;

        return Livro.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .isbn(dto.getIsbn())
                .dataPublicacao(dto.getDataPublicacao())
                .isDisponivel(dto.getDisponivel() == null ? true : dto.getDisponivel()) // ✅ define true por padrão
                .build();
    }

    public LivroDTO toDtoShallow(Livro livro) {
        if (livro == null) return null;

        return LivroDTO.builder()
                .id(livro.getId())
                .nome(livro.getNome())
                .isbn(livro.getIsbn())
                .dataPublicacao(livro.getDataPublicacao())
                .disponivel(livro.isDisponivel()) // ✅ mantém o mesmo comportamento
                .build();
    }
}
