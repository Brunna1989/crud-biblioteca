package com.br.biblioteca.mappers;

import com.br.biblioteca.dtos.LocatarioDTO;
import com.br.biblioteca.entities.Locatario;
import com.br.biblioteca.entities.Aluguel;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocatarioMapper {

    public LocatarioDTO toDto(Locatario locatario) {
        if (locatario == null) return null;

        List<Long> alugueisIds = locatario.getAlugueis() == null
                ? Collections.emptyList()
                : locatario.getAlugueis().stream()
                .map(Aluguel::getId)
                .collect(Collectors.toList());

        return LocatarioDTO.builder()
                .id(locatario.getId())
                .nome(locatario.getNome())
                .sexo(locatario.getSexo())
                .telefone(locatario.getTelefone())
                .email(locatario.getEmail())
                .dataNascimento(locatario.getDataNascimento())
                .cpf(locatario.getCpf())
                .alugueisIds(alugueisIds)
                .build();
    }

    public Locatario toEntity(LocatarioDTO dto) {
        if (dto == null) return null;
        return Locatario.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .sexo(dto.getSexo())
                .telefone(dto.getTelefone())
                .email(dto.getEmail())
                .dataNascimento(dto.getDataNascimento())
                .cpf(dto.getCpf())
                .build();
    }
}
