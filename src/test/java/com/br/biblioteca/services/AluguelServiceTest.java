package com.br.biblioteca.services;

import com.br.biblioteca.dtos.*;
import com.br.biblioteca.entities.*;
import com.br.biblioteca.mappers.*;
import com.br.biblioteca.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AluguelServiceTest {

    @InjectMocks
    private AluguelService aluguelService;

    @Mock private AutorRepository autorRepository;
    @Mock private LivroRepository livroRepository;
    @Mock private LocatarioRepository locatarioRepository;
    @Mock private AluguelRepository aluguelRepository;

    @Mock private AutorMapper autorMapper;
    @Mock private LivroMapper livroMapper;
    @Mock private LocatarioMapper locatarioMapper;
    @Mock private AluguelMapper aluguelMapper;

    private Autor autor;
    private AutorDTO autorDTO;
    private Livro livro;
    private LivroDTO livroDTO;
    private Locatario locatario;
    private LocatarioDTO locatarioDTO;
    private Aluguel aluguel;
    private AluguelDTO aluguelDTO;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        autor = Autor.builder()
                .id(1L)
                .nome("Machado de Assis")
                .cpf("12345678900")
                .sexo("M")
                .anoNascimento(1839)
                .livros(new ArrayList<>())
                .build();

        autorDTO = AutorDTO.builder()
                .id(1L)
                .nome("Machado de Assis")
                .cpf("12345678900")
                .sexo("M")
                .anoNascimento(1839)
                .build();

        livro = Livro.builder()
                .id(1L)
                .nome("Dom Casmurro")
                .isbn("9788572327427")
                .dataPublicacao(LocalDate.of(1899, 1, 1))
                .autores(List.of(autor))
                .isDisponivel(true)
                .alugueis(new ArrayList<>())
                .build();

        livroDTO = LivroDTO.builder()
                .id(1L)
                .nome("Dom Casmurro")
                .isbn("9788572327427")
                .dataPublicacao(LocalDate.of(1899, 1, 1))
                .disponivel(true)
                .autores(List.of(autorDTO))
                .autoresIds(List.of(1L))
                .build();

        locatario = Locatario.builder()
                .id(1L)
                .nome("João Silva")
                .cpf("98765432100")
                .email("joao@email.com")
                .telefone("123456789")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .alugueis(new ArrayList<>())
                .build();

        locatarioDTO = LocatarioDTO.builder()
                .id(1L)
                .nome("João Silva")
                .cpf("98765432100")
                .email("joao@email.com")
                .telefone("123456789")
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .build();

        aluguel = Aluguel.builder()
                .id(1L)
                .locatario(locatario)
                .livros(new ArrayList<>(List.of(livro)))
                .dataRetirada(LocalDate.now())
                .dataDevolucao(null)
                .build();

        aluguelDTO = AluguelDTO.builder()
                .id(1L)
                .locatarioId(1L)
                .livros(List.of(livroDTO))
                .dataRetirada(LocalDate.now())
                .dataDevolucao(null)
                .build();
    }

    @Test
    void deveCriarAutorComSucesso() {
        when(autorMapper.toEntity(autorDTO)).thenReturn(autor);
        when(autorRepository.save(any())).thenReturn(autor);
        when(autorMapper.toDto(autor)).thenReturn(autorDTO);

        AutorDTO result = aluguelService.criarAutor(autorDTO);

        assertNotNull(result);
        assertEquals("Machado de Assis", result.getNome());
        verify(autorRepository, times(1)).save(any());
    }

    @Test
    void deveAtualizarAutorExistente() {
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(autorRepository.save(any())).thenReturn(autor);
        when(autorMapper.toDto(autor)).thenReturn(autorDTO);

        AutorDTO result = aluguelService.atualizarAutor(1L, autorDTO);

        assertEquals("Machado de Assis", result.getNome());
    }

    @Test
    void deveLancarExcecaoAoAtualizarAutorNaoExistente() {
        when(autorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                aluguelService.atualizarAutor(99L, autorDTO));
    }

    @Test
    void deveCriarLivroComSucesso() {
        when(livroMapper.toEntity(livroDTO)).thenReturn(livro);
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(livroRepository.save(any())).thenReturn(livro);
        when(livroMapper.toDto(livro)).thenReturn(livroDTO);

        LivroDTO result = aluguelService.criarLivroComNovosAutores(livroDTO);

        assertNotNull(result);
        assertEquals("Dom Casmurro", result.getNome());
    }

    @Test
    void deveAtualizarLivroComSucesso() {
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(livroRepository.save(any())).thenReturn(livro);
        when(livroMapper.toDto(livro)).thenReturn(livroDTO);

        LivroDTO result = aluguelService.atualizarLivro(1L, livroDTO);

        assertNotNull(result);
        assertEquals("Dom Casmurro", result.getNome());
    }

    @Test
    void deveLancarExcecaoAoBuscarLivroInexistente() {
        when(livroRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                aluguelService.buscarLivroPorId(99L));
    }

    @Test
    void deveCriarLocatarioComSucesso() {
        when(locatarioMapper.toEntity(locatarioDTO)).thenReturn(locatario);
        when(locatarioRepository.save(any())).thenReturn(locatario);
        when(locatarioMapper.toDto(locatario)).thenReturn(locatarioDTO);

        LocatarioDTO result = aluguelService.criarLocatario(locatarioDTO);

        assertNotNull(result);
        assertEquals("João Silva", result.getNome());
    }

    @Test
    void deveCriarAluguelComSucesso() {
        livro.setDisponivel(true);
        when(locatarioRepository.findById(1L)).thenReturn(Optional.of(locatario));
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(aluguelRepository.save(any())).thenReturn(aluguel);
        when(aluguelMapper.toDto(any())).thenReturn(aluguelDTO);

        AluguelDTO result = aluguelService.criarAluguel(aluguelDTO);

        assertNotNull(result);
        assertEquals(1, result.getLivros().size());
        assertFalse(livro.isDisponivel()); // livro agora está alugado
    }

    @Test
    void deveLancarErroAoCriarAluguelSemLivros() {
        AluguelDTO dtoSemLivros = AluguelDTO.builder()
                .locatarioId(1L)
                .livros(Collections.emptyList())
                .build();

        assertThrows(IllegalArgumentException.class, () ->
                aluguelService.criarAluguel(dtoSemLivros));
    }

    @Test
    void deveFinalizarAluguelComSucesso() {
        livro.setDisponivel(false);
        when(aluguelRepository.findById(1L)).thenReturn(Optional.of(aluguel));

        aluguelService.finalizarAluguel(1L);

        assertNotNull(aluguel.getDataDevolucao());
        assertTrue(livro.isDisponivel());
        verify(aluguelRepository, times(1)).save(aluguel);
    }

    @Test
    void deveLancarErroAoFinalizarAluguelInexistente() {
        when(aluguelRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                aluguelService.finalizarAluguel(99L));
    }
}
