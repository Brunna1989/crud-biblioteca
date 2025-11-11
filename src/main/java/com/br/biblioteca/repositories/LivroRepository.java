package com.br.biblioteca.repositories;

import com.br.biblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository <Livro, Long> {
}
