package com.br.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Locatario extends JpaRepository <Locatario,Long> {
}
