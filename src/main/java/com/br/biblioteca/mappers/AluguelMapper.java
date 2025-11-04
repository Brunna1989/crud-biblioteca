package com.br.biblioteca.mappers;

import com.br.biblioteca.dtos.AluguelDTO;
import com.br.biblioteca.dtos.LivroDTO;
import com.br.biblioteca.entities.Aluguel;
import com.br.biblioteca.entities.Livro;
import com.br.biblioteca.entities.Locatario;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AluguelMapper {

    private final LivroMapper livroMapper;
    private final LocatarioMapper locatarioMapper;

    public AluguelMapper(LivroMapper livroMapper, LocatarioMapper locatarioMapper) {
        this.livroMapper = livroMapper;
        this.locatarioMapper = locatarioMapper;
    }

    public AluguelDTO toDto(Aluguel aluguel) {
        if (aluguel == null) return null;

        List<LivroDTO> livros = aluguel.getLivros() == null
                ? Collections.emptyList()
                : aluguel.getLivros().stream()
                .map(livroMapper::toDtoShallow) // evita ciclos
                .collect(Collectors.toList());

        Long locatarioId = aluguel.getLocatario() == null ? null : aluguel.getLocatario().getId();

        return AluguelDTO.builder()
                .id(aluguel.getId())
                .dataRetirada(aluguel.getDataRetirada())
                .dataDevolucao(aluguel.getDataDevolucao())
                .locatarioId(locatarioId)
                .livros(livros)
                .build();
    }

    public Aluguel toEntity(AluguelDTO dto) {
        if (dto == null) return null;

        List<Livro> livros = dto.getLivros() == null
                ? null
                : dto.getLivros().stream()
                .map(lDto -> Livro.builder().id(lDto.getId()).build())
                .collect(Collectors.toList());

        Locatario locatario = dto.getLocatarioId() == null
                ? null
                : Locatario.builder().id(dto.getLocatarioId()).build();

        return Aluguel.builder()
                .id(dto.getId())
                .dataRetirada(dto.getDataRetirada())
                .dataDevolucao(dto.getDataDevolucao())
                .locatario(locatario)
                .livros(livros)
                .build();
    }
}
