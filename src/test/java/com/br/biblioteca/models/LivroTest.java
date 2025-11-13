package com.br.biblioteca.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class LivroTest {

    private static Validator validator;
    private Livro livro;
    private Autor autor;
    private Aluguel aluguel;

    @BeforeAll
    static void configurarValidador() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    void setUp() {
        livro = new Livro();
        livro.setId(1L);
        livro.setNome("Clean Code");
        livro.setIsbn("1234567890123");
        livro.setDataPublicacao(LocalDate.of(2008, 8, 1));
        livro.setAlugado(false);

        autor = new Autor();
        autor.setId(5L);
        autor.setNome("Robert C. Martin");
        autor.setCpf("98765432100");
        autor.setAnoNascimento("1952");

        aluguel = new Aluguel();
        aluguel.setId(20L);
        aluguel.setDataDevolucao(LocalDate.now().plusDays(5));

        List<Autor> autores = new ArrayList<>();
        autores.add(autor);
        livro.setAutores(autores);

        List<Aluguel> alugueis = new ArrayList<>();
        alugueis.add(aluguel);
        livro.setAlugueis(alugueis);
    }

    @Test
    void deveCriarLivroComSucesso() {
        assertEquals(1L, livro.getId());
        assertEquals("Clean Code", livro.getNome());
        assertEquals("1234567890123", livro.getIsbn());
        assertEquals(LocalDate.of(2008, 8, 1), livro.getDataPublicacao());
        assertFalse(livro.isAlugado());
        assertEquals(1, livro.getAutores().size());
        assertEquals("Robert C. Martin", livro.getAutores().get(0).getNome());
        assertEquals(1, livro.getAlugueis().size());
        assertEquals(20L, livro.getAlugueis().get(0).getId());
    }

    @Test
    void deveGerarErrosDeValidacaoQuandoCamposForemInvalidos() {
        Livro livroInvalido = new Livro();
        livroInvalido.setNome("");
        livroInvalido.setIsbn("");
        livroInvalido.setDataPublicacao(null);

        Set<ConstraintViolation<Livro>> violacoes = validator.validate(livroInvalido);
        assertFalse(violacoes.isEmpty());
    }

    @Test
    void deveTestarGettersESetters() {
        Livro novo = new Livro();
        novo.setId(2L);
        novo.setNome("Effective Java");
        novo.setIsbn("9876543210123");
        novo.setDataPublicacao(LocalDate.of(2018, 1, 1));
        novo.setAlugado(true);

        assertEquals(2L, novo.getId());
        assertEquals("Effective Java", novo.getNome());
        assertEquals("9876543210123", novo.getIsbn());
        assertEquals(LocalDate.of(2018, 1, 1), novo.getDataPublicacao());
        assertTrue(novo.isAlugado());
    }

    @Test
    void deveAdicionarAutoresAoLivro() {
        Autor novoAutor = new Autor();
        novoAutor.setId(6L);
        novoAutor.setNome("Joshua Bloch");
        novoAutor.setCpf("11122233344");
        novoAutor.setAnoNascimento("1961");

        livro.getAutores().add(novoAutor);

        assertEquals(2, livro.getAutores().size());
        assertTrue(livro.getAutores().contains(novoAutor));
    }

    @Test
    void deveAdicionarAlugueisAoLivro() {
        Aluguel novoAluguel = new Aluguel();
        novoAluguel.setId(25L);
        novoAluguel.setDataDevolucao(LocalDate.now().plusDays(7));

        livro.getAlugueis().add(novoAluguel);

        assertEquals(2, livro.getAlugueis().size());
        assertTrue(livro.getAlugueis().contains(novoAluguel));
    }

    @Test
    void deveTestarEqualsEHashCode() {
        Livro livro1 = new Livro(1L, "Clean Code", "1234567890123", LocalDate.of(2008, 8, 1), false, new ArrayList<>(), new ArrayList<>());
        Livro livro2 = new Livro(1L, "Clean Code", "1234567890123", LocalDate.of(2008, 8, 1), false, new ArrayList<>(), new ArrayList<>());
        Livro livro3 = new Livro(2L, "Effective Java", "9876543210123", LocalDate.of(2018, 1, 1), true, new ArrayList<>(), new ArrayList<>());

        assertEquals(livro1, livro2, "Livros com mesmos dados devem ser iguais");
        assertNotEquals(livro1, livro3, "Livros com dados diferentes não devem ser iguais");

        assertEquals(livro1.hashCode(), livro2.hashCode(), "Os hashCode's devem ser iguais para objetos iguais");
        assertNotEquals(livro1.hashCode(), livro3.hashCode(), "Os hashCode's devem ser diferentes para objetos diferentes");
    }

}
