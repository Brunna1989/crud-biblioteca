package com.br.biblioteca.services;

import com.br.biblioteca.exceptions.LivroException;
import com.br.biblioteca.models.Autor;
import com.br.biblioteca.models.Livro;
import com.br.biblioteca.repositories.AutorRepository;
import com.br.biblioteca.repositories.LivroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final ObjectMapper objectMapper;



    public Livro salvar(Livro livro) {
        try {
            if (livro.getAutores() == null || livro.getAutores().isEmpty()) {
                throw new LivroException("O livro deve ter pelo menos um autor associado.");
            }

            List<Autor> autores = livro.getAutores().stream()
                    .map(a -> autorRepository.findById(a.getId())
                            .orElseThrow(() -> new LivroException("Autor com ID " + a.getId() + " não encontrado.")))
                    .collect(Collectors.toList());

            livro.setAutores(autores);

            Livro livroSalvo = livroRepository.save(livro);

            for (Autor autor : autores) {
                autor.getLivros().add(livroSalvo);
                autorRepository.save(autor);
            }

            return livroSalvo;

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
            existente.setNome(dadosAtualizados.getNome());
            existente.setIsbn(dadosAtualizados.getIsbn());
            existente.setDataPublicacao(dadosAtualizados.getDataPublicacao());

            if (dadosAtualizados.getAutores() != null && !dadosAtualizados.getAutores().isEmpty()) {
                List<Autor> autores = dadosAtualizados.getAutores().stream()
                        .map(a -> autorRepository.findById(a.getId())
                                .orElseThrow(() -> new LivroException("Autor com ID " + a.getId() + " não encontrado.")))
                        .collect(Collectors.toList());

                existente.setAutores(autores);

                for (Autor autor : autores) {
                    if (!autor.getLivros().contains(existente)) {
                        autor.getLivros().add(existente);
                        autorRepository.save(autor);
                    }
                }
            }

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
            // Remove o livro da lista de cada autor antes de deletar
            for (Autor autor : livro.getAutores()) {
                autor.getLivros().remove(livro);
                autorRepository.save(autor);
            }

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

    public List<Livro> buscarLivroPorAutor(Long autorId) {
        try {
            return livroRepository.findByAutores_Id(autorId);
        } catch (Exception e) {
            throw new LivroException("Erro ao buscar livros do autor: " + e.getMessage());
        }
    }
}


