package com.br.biblioteca.models;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AluguelTest {

    private static Validator validator;

    @BeforeAll
    static void configurarValidador() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveValidarAluguelCorretamente() {
        Locatario locatario = new Locatario();
        locatario.setId(1L);
        locatario.setNome("Maria Souza");

        Livro livro = new Livro();
        livro.setId(10L);
        livro.setNome("Use a Cabeça! Java");

        List<Livro> livros = new ArrayList<>();
        livros.add(livro);

        Aluguel aluguel = new Aluguel();
        aluguel.setId(100L);
        aluguel.setLocatario(locatario);
        aluguel.setLivros(livros);
        aluguel.setDataDevolucao(LocalDate.now().plusDays(2));

        Set<ConstraintViolation<Aluguel>> violacoes = validator.validate(aluguel);

        assertTrue(violacoes.isEmpty(),
                "Nenhuma violação esperada para um aluguel válido");
    }

    @Test
    void deveInvalidarAluguelSemDataDeDevolucao() {
        Aluguel aluguel = new Aluguel();
        aluguel.setLocatario(new Locatario());
        aluguel.setLivros(new ArrayList<>());
        aluguel.setDataDevolucao(null);

        Set<ConstraintViolation<Aluguel>> violacoes = validator.validate(aluguel);

        assertFalse(violacoes.isEmpty(),
                "Deveria haver violação por data de devolução nula");
    }

    @Test
    void deveRetornarValoresCorretosNosGettersESetters() {
        Locatario locatario = new Locatario();
        locatario.setId(1L);
        locatario.setNome("João da Silva");

        Livro livro = new Livro();
        livro.setId(5L);
        livro.setNome("Clean Code");

        List<Livro> listaLivros = new ArrayList<>();
        listaLivros.add(livro);

        Aluguel aluguel = new Aluguel();
        aluguel.setId(50L);
        aluguel.setLocatario(locatario);
        aluguel.setLivros(listaLivros);
        aluguel.setDataDevolucao(LocalDate.now().plusDays(3));

        assertEquals(50L, aluguel.getId());
        assertEquals("João da Silva", aluguel.getLocatario().getNome());
        assertEquals("Clean Code", aluguel.getLivros().get(0).getNome());
        assertEquals(LocalDate.now().plusDays(3), aluguel.getDataDevolucao());
    }

    @Test
    void deveAdicionarLivrosAoAluguel() {
        Aluguel aluguel = new Aluguel();
        Livro livro1 = new Livro();
        livro1.setNome("Java: Como Programar");

        Livro livro2 = new Livro();
        livro2.setNome("O Programador Pragmático");

        aluguel.getLivros().add(livro1);
        aluguel.getLivros().add(livro2);

        assertEquals(2, aluguel.getLivros().size());
        assertEquals("Java: Como Programar", aluguel.getLivros().get(0).getNome());
        assertEquals("O Programador Pragmático", aluguel.getLivros().get(1).getNome());
    }

    @Test
    void deveAssociarLocatarioCorretamente() {
        Locatario locatario = new Locatario();
        locatario.setId(7L);
        locatario.setNome("Carlos Pereira");

        Aluguel aluguel = new Aluguel();
        aluguel.setLocatario(locatario);

        assertNotNull(aluguel.getLocatario());
        assertEquals("Carlos Pereira", aluguel.getLocatario().getNome());
        assertEquals(7L, aluguel.getLocatario().getId());
    }

    @Test
    void deveCriarAluguelComDataPadrao() {
        Aluguel aluguel = new Aluguel();
        assertEquals(LocalDate.now().plusDays(2), aluguel.getDataDevolucao(),
                "A data padrão deve ser hoje + 2 dias");
    }

    @Test
    void deveDiferenciarAlugueisComIdsDiferentes() {
        Aluguel aluguel1 = new Aluguel();
        aluguel1.setId(1L);

        Aluguel aluguel2 = new Aluguel();
        aluguel2.setId(2L);

        assertNotEquals(aluguel1, aluguel2);
        assertNotEquals(aluguel1.hashCode(), aluguel2.hashCode());
    }

    @Test
    void deveManterRelacionamentoBidirecionalComLivros() {
        Livro livro = new Livro();
        livro.setId(3L);
        livro.setNome("Clean Architecture");

        Aluguel aluguel = new Aluguel();
        aluguel.setId(5L);

        aluguel.getLivros().add(livro);
        livro.getAlugueis().add(aluguel);

        assertTrue(aluguel.getLivros().contains(livro));
        assertTrue(livro.getAlugueis().contains(aluguel));
    }

    @Test
    void devePermitirVariosLivrosEmUmUnicoAluguel() {
        Livro l1 = new Livro();
        l1.setNome("Effective Java");
        Livro l2 = new Livro();
        l2.setNome("Refactoring");
        Livro l3 = new Livro();
        l3.setNome("Design Patterns");

        Aluguel aluguel = new Aluguel();
        aluguel.getLivros().add(l1);
        aluguel.getLivros().add(l2);
        aluguel.getLivros().add(l3);

        assertEquals(3, aluguel.getLivros().size());
    }

}
