package com.br.biblioteca.models;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Test
    void deveValidarCpfComTamanhoIncorreto() {
        autor.setCpf("123");
        Set<ConstraintViolation<Autor>> violacoes = validator.validate(autor);
        assertFalse(violacoes.isEmpty());
    }

    @Test
    void devePermitirRelacionamentoComVariosLivros() {
        Livro livro2 = new Livro();
        livro2.setId(11L);
        livro2.setNome("Memórias Póstumas de Brás Cubas");
        livro2.setIsbn("9876543210987");
        livro2.setDataPublicacao(LocalDate.of(1881, 1, 1));

        autor.getLivros().add(livro2);

        assertEquals(2, autor.getLivros().size());
        assertEquals("Dom Casmurro", autor.getLivros().get(0).getNome());
        assertEquals("Memórias Póstumas de Brás Cubas", autor.getLivros().get(1).getNome());
    }

    @Test
    void deveTestarGettersESetters() {
        Autor novoAutor = new Autor();
        novoAutor.setId(5L);
        novoAutor.setNome("Clarice Lispector");
        novoAutor.setSexo("Feminino");
        novoAutor.setAnoNascimento("1920");
        novoAutor.setCpf("98765432100");

        assertEquals(5L, novoAutor.getId());
        assertEquals("Clarice Lispector", novoAutor.getNome());
        assertEquals("Feminino", novoAutor.getSexo());
        assertEquals("1920", novoAutor.getAnoNascimento());
        assertEquals("98765432100", novoAutor.getCpf());
    }

    @Test
    void deveTestarEqualsEHashCode() {
        Autor autor1 = new Autor(1L, "Machado de Assis", "Masculino", "1839", "12345678901", new ArrayList<>());
        Autor autor2 = new Autor(1L, "Machado de Assis", "Masculino", "1839", "12345678901", new ArrayList<>());
        Autor autor3 = new Autor(2L, "Clarice Lispector", "Feminino", "1920", "98765432100", new ArrayList<>());

        assertEquals(autor1, autor2);
        assertNotEquals(autor1, autor3);

        Set<Autor> autores = new HashSet<>();
        autores.add(autor1);
        autores.add(autor2);

        assertEquals(1, autores.size());
    }

    @Test
    void devePermitirAdicionarLivroAoAutor() {
        Livro novoLivro = new Livro();
        novoLivro.setId(15L);
        novoLivro.setNome("Quincas Borba");
        novoLivro.setIsbn("5555555555555");
        novoLivro.setDataPublicacao(LocalDate.of(1891, 1, 1));

        autor.getLivros().add(novoLivro);

        assertEquals(2, autor.getLivros().size());
        assertTrue(autor.getLivros().contains(novoLivro));
    }


}







