package com.br.biblioteca.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AutorTest {

    private Autor autor;
    private Livro livro;
    private static Validator validator;


    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        autor = new Autor();
        autor.setId(1L);
        autor.setNome("Machado de Assis");
        autor.setSexo("Masculino");
        autor.setAnoNascimento("1839");
        autor.setCpf("12345678901");

        livro = new Livro();
        livro.setId(10L);
        livro.setNome("Dom Casmurro");
        livro.setIsbn("1234567890123");
        livro.setDataPublicacao(java.time.LocalDate.of(1899, 1, 1));

        List<Livro> livros = new ArrayList<>();
        livros.add(livro);
        autor.setLivros(livros);
    }

    @Test
    void deveCriarAutorComSucesso() {
        Autor autor = new Autor();
        autor.setId(1L);
        autor.setNome(autor.getNome());
        autor.setSexo(autor.getSexo());
        autor.setAnoNascimento("1839");
        autor.setCpf("85794564123");

        livro = new Livro();
        livro.setId(10L);
        livro.setNome("Dom Casmurro");
        livro.setIsbn("1234567890123");
        livro.setDataPublicacao(java.time.LocalDate.of(1899, 1, 1));

        List<Livro> livros = new ArrayList<>();
        livros.add(livro);
        autor.setLivros(livros);

    }

    @Test
    void deveRetornarErrosDeValidacaoQuandoCamposObrigatoriosForemInvalidos() {
        Autor autorInvalido = new Autor();
        autorInvalido.setCpf("123");

        var violacoes = validator.validate(autorInvalido);

        assertFalse(violacoes.isEmpty(), "Deve haver violações de validação");
        violacoes.forEach(v -> System.out.println(v.getPropertyPath() + ": " + v.getMessage()));
    }


}







