package com.br.biblioteca.repositories;

import com.br.biblioteca.entities.Locatario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocatarioRepository extends JpaRepository<Locatario,Long> {
}
