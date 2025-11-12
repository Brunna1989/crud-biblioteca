package com.br.biblioteca.services;

import com.br.biblioteca.exceptions.LocatarioException;
import com.br.biblioteca.models.Locatario;
import com.br.biblioteca.repositories.LocatarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocatarioService {

    private final LocatarioRepository locatarioRepository;

    public Locatario salvar(Locatario locatario) {
        if (locatarioRepository.existsByCpf(locatario.getCpf())) {
            throw new LocatarioException("Já existe um locatário cadastrado com este CPF.");
        }
        if (locatarioRepository.existsByEmail(locatario.getEmail())) {
            throw new LocatarioException("Já existe um locatário cadastrado com este e-mail.");
        }

        return locatarioRepository.save(locatario);
    }
}


