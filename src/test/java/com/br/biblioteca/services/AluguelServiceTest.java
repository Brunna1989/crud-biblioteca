package com.br.biblioteca.services;

import com.br.biblioteca.dtos.*;
import com.br.biblioteca.entities.*;
import com.br.biblioteca.mappers.*;
import com.br.biblioteca.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AluguelServiceTest {

    @Mock private AutorRepository autorRepository;
    @Mock private LivroRepository livroRepository;
    @Mock private LocatarioRepository locatarioRepository;
    @Mock private AluguelRepository aluguelRepository;

    @Mock private AutorMapper autorMapper;
    @Mock private LivroMapper livroMapper;
    @Mock private LocatarioMapper locatarioMapper;
    @Mock private AluguelMapper aluguelMapper;

    @InjectMocks
    private AluguelService aluguelService;

    private Autor autor;
    private Livro livro;
    private Locatario locatario;
    private Aluguel aluguel;

    private AutorDTO autorDTO;
    private LivroDTO livroDTO;
    private LocatarioDTO locatarioDTO;
    private AluguelDTO aluguelDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        autor = Autor.builder().id(1L).nome("Machado de Assis").build();
        livro = Livro.builder().id(1L).nome("Dom Casmurro").autores(List.of(autor)).build();
        locatario = Locatario.builder().id(1L).nome("Brunna Dornelles").build();
        aluguel = Aluguel.builder().id(1L).dataRetirada(LocalDate.now()).dataDevolucao(null)
                .locatario(locatario).livros(List.of(livro)).build();

        autorDTO = AutorDTO.builder().id(1L).nome("Machado de Assis").build();
        livroDTO = LivroDTO.builder().id(1L).nome("Dom Casmurro").autores(List.of(autorDTO)).build();
        locatarioDTO = LocatarioDTO.builder().id(1L).nome("Brunna Dornelles").build();
        aluguelDTO = AluguelDTO.builder()
                .id(1L)
                .locatarioId(1L)
                .livros(List.of(livroDTO))
                .dataRetirada(LocalDate.now())
                .dataDevolucao(null)
                .build();
    }



    @Test
    @DisplayName("Deve criar autor com sucesso")
    void deveCriarAutor() {
        when(autorMapper.toEntity(autorDTO)).thenReturn(autor);
        when(autorRepository.save(autor)).thenReturn(autor);
        when(autorMapper.toDto(autor)).thenReturn(autorDTO);

        AutorDTO result = aluguelService.criarAutor(autorDTO);

        assertEquals("Machado de Assis", result.getNome());
        verify(autorRepository).save(autor);
    }

    @Test
    @DisplayName("Deve atualizar autor existente")
    void deveAtualizarAutor() {
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(autorRepository.save(autor)).thenReturn(autor);
        when(autorMapper.toDto(autor)).thenReturn(autorDTO);

        AutorDTO result = aluguelService.atualizarAutor(1L, autorDTO);

        assertEquals(autorDTO, result);
        verify(autorRepository).save(any(Autor.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar autor inexistente")
    void deveLancarExcecaoAtualizarAutorInexistente() {
        when(autorRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> aluguelService.atualizarAutor(1L, autorDTO));
    }

    @Test
    @DisplayName("Deve listar todos os autores")
    void deveListarAutores() {
        when(autorRepository.findAll()).thenReturn(List.of(autor));
        when(autorMapper.toDto(autor)).thenReturn(autorDTO);

        List<AutorDTO> result = aluguelService.listarAutores();
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Deve buscar autor por nome")
    void deveBuscarAutorPorNome() {
        when(autorRepository.findAll()).thenReturn(List.of(autor));
        when(autorMapper.toDto(autor)).thenReturn(autorDTO);

        AutorDTO result = aluguelService.buscarAutorPorNome("Machado de Assis");
        assertEquals("Machado de Assis", result.getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar autor inexistente")
    void deveLancarExcecaoBuscarAutorPorNome() {
        when(autorRepository.findAll()).thenReturn(List.of());
        assertThrows(EntityNotFoundException.class, () -> aluguelService.buscarAutorPorNome("Inexistente"));
    }

    @Test
    @DisplayName("Deve deletar autor sem livros associados")
    void deveDeletarAutor() {
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        autor.setLivros(Collections.emptyList());

        aluguelService.deletarAutor(1L);
        verify(autorRepository).delete(autor);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar autor com livros associados")
    void deveLancarExcecaoDeletarAutorComLivros() {
        autor.setLivros(List.of(livro));
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));

        assertThrows(IllegalStateException.class, () -> aluguelService.deletarAutor(1L));
    }


    @Test
    @DisplayName("Deve criar livro com autores")
    void deveCriarLivro() {
        when(livroMapper.toEntity(livroDTO)).thenReturn(livro);
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(livroRepository.save(any())).thenReturn(livro);
        when(livroMapper.toDto(livro)).thenReturn(livroDTO);

        LivroDTO result = aluguelService.criarLivro(livroDTO);
        assertEquals("Dom Casmurro", result.getNome());
    }

    @Test
    @DisplayName("Deve atualizar livro existente")
    void deveAtualizarLivro() {
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(livroRepository.save(livro)).thenReturn(livro);
        when(livroMapper.toDto(livro)).thenReturn(livroDTO);

        LivroDTO result = aluguelService.atualizarLivro(1L, livroDTO);
        assertEquals(livroDTO, result);
    }

    @Test
    @DisplayName("Deve buscar livro por id")
    void deveBuscarLivroPorId() {
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(livroMapper.toDto(livro)).thenReturn(livroDTO);

        LivroDTO result = aluguelService.buscarLivroPorId(1L);
        assertEquals("Dom Casmurro", result.getNome());
    }

    @Test
    @DisplayName("Deve listar livros disponíveis")
    void deveListarLivrosDisponiveis() {
        livro.setAlugueis(List.of(aluguel));
        aluguel.setDataDevolucao(LocalDate.now());
        when(livroRepository.findAll()).thenReturn(List.of(livro));
        when(livroMapper.toDto(livro)).thenReturn(livroDTO);

        List<LivroDTO> result = aluguelService.listarLivrosDisponiveis();
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Deve listar livros alugados")
    void deveListarLivrosAlugados() {
        livro.setAlugueis(List.of(aluguel));
        when(livroRepository.findAll()).thenReturn(List.of(livro));
        when(livroMapper.toDto(livro)).thenReturn(livroDTO);

        List<LivroDTO> result = aluguelService.listarLivrosAlugados();
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Deve deletar livro sem aluguel")
    void deveDeletarLivro() {
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        livro.setAlugueis(Collections.emptyList());
        livro.setAutores(List.of(autor));
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));

        aluguelService.deletarLivro(1L);
        verify(livroRepository).delete(livro);
    }



    @Test
    @DisplayName("Deve criar locatário")
    void deveCriarLocatario() {
        when(locatarioMapper.toEntity(locatarioDTO)).thenReturn(locatario);
        when(locatarioRepository.save(locatario)).thenReturn(locatario);
        when(locatarioMapper.toDto(locatario)).thenReturn(locatarioDTO);

        LocatarioDTO result = aluguelService.criarLocatario(locatarioDTO);
        assertEquals("Brunna Dornelles", result.getNome());
    }

    @Test
    @DisplayName("Deve listar locatários")
    void deveListarLocatarios() {
        when(locatarioRepository.findAll()).thenReturn(List.of(locatario));
        when(locatarioMapper.toDto(locatario)).thenReturn(locatarioDTO);

        List<LocatarioDTO> result = aluguelService.listarLocatarios();
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar locatário com devolução pendente")
    void deveLancarExcecaoDeletarLocatarioComDevolucaoPendente() {
        aluguel.setDataDevolucao(null);
        locatario.setAlugueis(List.of(aluguel));
        when(locatarioRepository.findById(1L)).thenReturn(Optional.of(locatario));

        assertThrows(IllegalStateException.class, () -> aluguelService.deletarLocatario(1L));
    }



    @Test
    @DisplayName("Deve criar aluguel com sucesso")
    void deveCriarAluguel() {
        when(locatarioRepository.findById(1L)).thenReturn(Optional.of(locatario));
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(aluguelRepository.save(any())).thenReturn(aluguel);
        when(aluguelMapper.toDto(aluguel)).thenReturn(aluguelDTO);

        AluguelDTO result = aluguelService.criarAluguel(aluguelDTO);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar aluguel sem livros")
    void deveLancarExcecaoCriarAluguelSemLivros() {
        aluguelDTO.setLivros(Collections.emptyList());
        assertThrows(IllegalArgumentException.class, () -> aluguelService.criarAluguel(aluguelDTO));
    }

    @Test
    @DisplayName("Deve listar todos os alugueis")
    void deveListarAlugueis() {
        when(aluguelRepository.findAll()).thenReturn(List.of(aluguel));
        when(aluguelMapper.toDto(aluguel)).thenReturn(aluguelDTO);

        List<AluguelDTO> result = aluguelService.listarAlugueis();
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Deve finalizar aluguel")
    void deveFinalizarAluguel() {
        when(aluguelRepository.findById(1L)).thenReturn(Optional.of(aluguel));

        aluguelService.finalizarAluguel(1L);
        verify(aluguelRepository).save(aluguel);
        assertNotNull(aluguel.getDataDevolucao());
    }
}
