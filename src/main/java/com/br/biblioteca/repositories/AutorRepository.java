package com.br.biblioteca.repositories;

import com.br.biblioteca.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  AutorRepository extends JpaRepository<Autor,Long> {
}
