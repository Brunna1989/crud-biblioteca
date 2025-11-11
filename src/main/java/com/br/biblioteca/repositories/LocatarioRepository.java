package com.br.biblioteca.repositories;

import com.br.biblioteca.model.Locatario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocatarioRepository extends JpaRepository <Locatario,Long> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}
