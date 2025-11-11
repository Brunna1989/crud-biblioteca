package com.br.biblioteca.repositories;

import com.br.biblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository <Livro, Long> {
    List<Livro> findByNomeContainingIgnoreCase(String nome);
    List<Livro> findByAutores_Id(Long autorId);
    List<Livro> findByDisponivelTrue();
    List<Livro> findByDisponivelFalse();

}
