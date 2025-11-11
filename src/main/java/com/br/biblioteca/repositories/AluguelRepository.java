package com.br.biblioteca.repositories;

import com.br.biblioteca.model.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AluguelRepository extends JpaRepository<Aluguel, Long> {
    List<Aluguel> findByLocatario_Id(Long locatarioId);
}
