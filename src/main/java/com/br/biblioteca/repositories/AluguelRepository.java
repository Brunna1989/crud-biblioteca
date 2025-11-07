package com.br.biblioteca.repositories;

import com.br.biblioteca.entities.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AluguelRepository extends JpaRepository<Aluguel,Long> {

    boolean existsByLivrosIdAndDataDevolucaoIsNull(Long livroId);


}
