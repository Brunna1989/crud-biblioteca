package com.br.biblioteca.services;

import com.br.biblioteca.exceptions.AutorException;
import com.br.biblioteca.models.Autor;
import com.br.biblioteca.repositories.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository autorRepository;

    public Autor salvarAutor(Autor autor) {
        try {
            return autorRepository.save(autor);
        } catch (Exception e) {
            throw new AutorException("Erro ao salvar o autor: " + e.getMessage());
        }
    }

    public List<Autor> listarTodosAutores(){
        return autorRepository.findAll();
    }

    public Autor buscarAutorPorId(Long id) {
        return autorRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Autor com ID " + id + " não encontrado no sistema"));
    }

    public Autor buscaAutorPorNome(String nome) {
        List<Autor> autores = autorRepository.findByNomeContainingIgnoreCase(nome);
        if(autores.size() > 0) {
            return autores.get(0);
        } else {
            throw new AutorException(" Nenhum autor encontrado com o nome: " + nome);
        }
    }

    public void deletarAutor(Long id) {
        Autor autor = buscarAutorPorId(id);

        if (autor.getLivros() != null && !autor.getLivros().isEmpty()) {
            throw new AutorException("Autor não pode ser excluído, pois possui livros associados.");
        }

        try {
            autorRepository.delete(autor);
        } catch (Exception e) {
            throw new AutorException("Erro ao excluir o autor: " + e.getMessage());
        }
    }
}
