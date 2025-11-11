package com.br.biblioteca.services;

import com.br.biblioteca.exceptions.LivroException;
import com.br.biblioteca.models.Livro;
import com.br.biblioteca.repositories.LivroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final ObjectMapper objectMapper;


    public Livro salvar(Livro livro) {
        try {
            return livroRepository.save(livro);
        } catch (Exception e) {
            throw new LivroException("Erro ao salvar o livro: " + e.getMessage());
        }
    }

    public List<Livro> listarTodos() {
        try {
            return livroRepository.findAll();
        } catch (Exception e) {
            throw new LivroException("Erro ao listar os livros: " + e.getMessage());
        }
    }

    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new LivroException("Livro com ID " + id + " não encontrado."));
    }

    public Livro atualizar(Long id, Livro dadosAtualizados) {
        Livro existente = buscarPorId(id);
        try {
            objectMapper.updateValue(existente, dadosAtualizados);
            return livroRepository.save(existente);
        } catch (Exception e) {
            throw new LivroException("Erro ao atualizar o livro: " + e.getMessage());
        }
    }

    public void deletar(Long id) {
        Livro livro = buscarPorId(id);

        if (livro.isAlugado()) {
            throw new LivroException("Livro está alugado e não pode ser excluído.");
        }

        try {
            livroRepository.delete(livro);
        } catch (Exception e) {
            throw new LivroException("Erro ao excluir o livro: " + e.getMessage());
        }
    }

    public List<Livro> listarDisponiveis() {
        try {
            return livroRepository.findByAlugadoFalse();
        } catch (Exception e) {
            throw new LivroException("Erro ao listar livros disponíveis: " + e.getMessage());
        }
    }

    public List<Livro> listarAlugados() {
        try {
            return livroRepository.findByAlugadoTrue();
        } catch (Exception e) {
            throw new LivroException("Erro ao listar livros alugados: " + e.getMessage());
        }
    }

    public List<Livro> buscarPorNome(String nome) {
        try {
            return livroRepository.findByNomeContainingIgnoreCase(nome);
        } catch (Exception e) {
            throw new LivroException("Erro ao buscar livros pelo nome: " + e.getMessage());
        }
    }

    public List<Livro> buscarPorAutor(Long autorId) {
        try {
            return livroRepository.findByAutores_Id(autorId);
        } catch (Exception e) {
            throw new LivroException("Erro ao buscar livros do autor: " + e.getMessage());
        }
    }


}


