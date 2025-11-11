package com.br.biblioteca.services;

import com.br.biblioteca.exceptions.AutorException;
import com.br.biblioteca.models.Autor;
import com.br.biblioteca.repositories.AutorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;

    private final ObjectMapper mapper;

    public Autor salvar(Object dto) {
        Autor autor = mapper.convertValue(dto, Autor.class);
        return autorRepository.save(autor);
    }

    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    public Autor buscarPorId(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new AutorException("Autor não encontrado para o ID: " + id));
    }

    public List<Autor> buscarPorNome(String nome) {
        return autorRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Autor atualizar(Long id, Object dto) {
        Autor existente = buscarPorId(id);
        Autor autorAtualizado = mapper.convertValue(dto, Autor.class);
        autorAtualizado.setId(existente.getId());
        return autorRepository.save(autorAtualizado);
    }

    public void deletar(Long id) {
        Autor autor = buscarPorId(id);
        if (!autor.getLivros().isEmpty()) {
            throw new AutorException("Autor possui livros associados e não pode ser excluído.");
        }
        autorRepository.delete(autor);
    }
}
