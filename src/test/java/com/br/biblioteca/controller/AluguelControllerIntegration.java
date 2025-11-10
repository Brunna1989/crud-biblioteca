package com.br.biblioteca.controller;

import com.br.biblioteca.dtos.*;
import com.br.biblioteca.repositories.*;
import com.br.biblioteca.services.AluguelService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class AluguelControllerIntegration {

    @Autowired
    private AluguelService aluguelService;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private LocatarioRepository locatarioRepository;

    @Autowired
    private AluguelRepository aluguelRepository;

    private AutorDTO autorDTO;
    private LivroDTO livroDTO;
    private LocatarioDTO locatarioDTO;

    @BeforeEach
    void setup() {
        autorDTO = AutorDTO.builder()
                .nome("Machado de Assis")
                .cpf("12345678900")
                .sexo("M")
                .anoNascimento(1839)
                .build();

        livroDTO = LivroDTO.builder()
                .nome("Dom Casmurro")
                .isbn("9788572327427")
                .dataPublicacao(LocalDate.of(1899, 1, 1))
                .build();

        locatarioDTO = LocatarioDTO.builder()
                .nome("João Silva")
                .cpf("98765432100")
                .email("joao@email.com")
                .build();
    }

    @Test
    void deveCriarAutorComSucesso() {
        AutorDTO criado = aluguelService.criarAutor(autorDTO);
        assertNotNull(criado.getId());
        assertEquals("Machado de Assis", criado.getNome());
    }

    @Test
    void deveAtualizarAutorExistente() {
        AutorDTO criado = aluguelService.criarAutor(autorDTO);
        criado.setNome("M. de Assis Atualizado");
        AutorDTO atualizado = aluguelService.atualizarAutor(criado.getId(), criado);
        assertEquals("M. de Assis Atualizado", atualizado.getNome());
    }

    @Test
    void deveLancarExcecaoAoAtualizarAutorNaoExistente() {
        assertThrows(EntityNotFoundException.class, () ->
                aluguelService.atualizarAutor(999L, autorDTO));
    }

    @Test
    void deveCriarLivroComAutorExistente() {
        AutorDTO autorCriado = aluguelService.criarAutor(autorDTO);
        livroDTO.setAutores(List.of(autorCriado));
        LivroDTO criado = aluguelService.criarLivroComNovosAutores(livroDTO);

        assertNotNull(criado.getId());
        assertEquals("Dom Casmurro", criado.getNome());
        assertEquals(1, criado.getAutores().size());
    }

    @Test
    void deveAtualizarLivroComSucesso() {
        AutorDTO autorCriado = aluguelService.criarAutor(autorDTO);
        livroDTO.setAutores(List.of(autorCriado));
        LivroDTO criado = aluguelService.criarLivroComNovosAutores(livroDTO);

        criado.setNome("Dom Casmurro Edição Revisada");
        LivroDTO atualizado = aluguelService.atualizarLivro(criado.getId(), criado);
        assertEquals("Dom Casmurro Edição Revisada", atualizado.getNome());
    }

    @Test
    void deveLancarExcecaoAoBuscarLivroInexistente() {
        assertThrows(EntityNotFoundException.class, () ->
                aluguelService.buscarLivroPorId(999L));
    }

    @Test
    void deveLancarErroAoFinalizarAluguelInexistente() {
        assertThrows(EntityNotFoundException.class, () ->
                aluguelService.finalizarAluguel(999L));
    }
}
