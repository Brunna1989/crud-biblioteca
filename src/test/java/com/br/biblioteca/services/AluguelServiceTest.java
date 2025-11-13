package com.br.biblioteca.services;

import com.br.biblioteca.exceptions.AutorException;
import com.br.biblioteca.models.Autor;
import com.br.biblioteca.repositories.AutorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AluguelServiceTest {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorService autorService;

    private Autor autor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        autor = Autor.builder()
                .id(1L)
                .nome("Machado de Assis")
                .sexo("Masculino")
                .anoNascimento("1839")
                .cpf("12345678901")
                .build();
    }

    @Test
    void deveSalvarAutorComSucesso() {
        when(autorRepository.save(autor)).thenReturn(autor);
        Autor resultado = autorService.salvarAutor(autor);
        assertEquals(autor, resultado);
        verify(autorRepository).save(autor);
    }

    @Test
    void deveLancarExcecaoAoSalvarAutor() {
        when(autorRepository.save(autor)).thenThrow(new RuntimeException("Erro de banco"));
        AutorException ex = assertThrows(AutorException.class, () -> autorService.salvarAutor(autor));
        assertTrue(ex.getMessage().contains("Erro ao salvar o autor"));
    }

    @Test
    void deveListarTodosAutores() {
        when(autorRepository.findAll()).thenReturn(List.of(autor));
        List<Autor> lista = autorService.listarTodosAutores();
        assertEquals(1, lista.size());
        assertEquals("Machado de Assis", lista.get(0).getNome());
        verify(autorRepository).findAll();
    }

    @Test
    void deveBuscarAutorPorIdComSucesso() {
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        Autor resultado = autorService.buscarAutorPorId(1L);
        assertEquals(autor, resultado);
        verify(autorRepository).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoAutorNaoEncontradoPorId() {
        when(autorRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> autorService.buscarAutorPorId(99L));
        assertTrue(ex.getMessage().contains("Autor com ID 99 não encontrado"));
    }

    @Test
    void deveBuscarAutorPorNomeComSucesso() {
        when(autorRepository.findByNomeContainingIgnoreCase("machado")).thenReturn(List.of(autor));
        Autor resultado = autorService.buscaAutorPorNome("machado");
        assertEquals(autor, resultado);
        verify(autorRepository).findByNomeContainingIgnoreCase("machado");
    }

    @Test
    void deveLancarExcecaoQuandoAutorNaoEncontradoPorNome() {
        when(autorRepository.findByNomeContainingIgnoreCase("desconhecido")).thenReturn(Collections.emptyList());
        AutorException ex = assertThrows(AutorException.class, () -> autorService.buscaAutorPorNome("desconhecido"));
        assertTrue(ex.getMessage().contains("Nenhum autor encontrado"));
    }

    @Test
    void deveDeletarAutorComSucesso() {
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        autor.setLivros(Collections.emptyList());
        autorService.deletarAutor(1L);
        verify(autorRepository).delete(autor);
    }

    @Test
    void deveLancarExcecaoAoDeletarAutorComLivros() {
        autor.setLivros(List.of(new com.br.biblioteca.models.Livro()));
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        AutorException ex = assertThrows(AutorException.class, () -> autorService.deletarAutor(1L));
        assertTrue(ex.getMessage().contains("possui livros associados"));
    }

    @Test
    void deveLancarExcecaoAoFalharNaExclusao() {
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        autor.setLivros(Collections.emptyList());
        doThrow(new RuntimeException("Erro ao excluir")).when(autorRepository).delete(autor);
        AutorException ex = assertThrows(AutorException.class, () -> autorService.deletarAutor(1L));
        assertTrue(ex.getMessage().contains("Erro ao excluir o autor"));
    }
}
