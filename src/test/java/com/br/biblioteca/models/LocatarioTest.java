package com.br.biblioteca.models;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class LocatarioTest {

    private Locatario locatario;
    private static Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        locatario = new Locatario();
        locatario.setId(1L);
        locatario.setNome("Brunna Dornelles");
        locatario.setSexo("Feminino");
        locatario.setTelefone("51999999999");
        locatario.setEmail("brunna@email.com");
        locatario.setDataNascimento(LocalDate.of(1995, 8, 15));
        locatario.setCpf("12345678901");

        Aluguel aluguel = new Aluguel();
        aluguel.setId(10L);
        List<Aluguel> alugueis = new ArrayList<>();
        alugueis.add(aluguel);
        locatario.setAlugueis(alugueis);
    }

    @Test
    void deveValidarLocatarioComSucesso() {
        Set<ConstraintViolation<Locatario>> violacoes = validator.validate(locatario);
        assertTrue(violacoes.isEmpty());
    }

    @Test
    void deveDetectarNomeEmBranco() {
        locatario.setNome("");
        Set<ConstraintViolation<Locatario>> violacoes = validator.validate(locatario);
        assertFalse(violacoes.isEmpty());
    }

    @Test
    void deveDetectarEmailInvalido() {
        locatario.setEmail("email_invalido");
        Set<ConstraintViolation<Locatario>> violacoes = validator.validate(locatario);
        assertFalse(violacoes.isEmpty());
    }

    @Test
    void deveDetectarCpfInvalido() {
        locatario.setCpf("1234");
        Set<ConstraintViolation<Locatario>> violacoes = validator.validate(locatario);
        assertFalse(violacoes.isEmpty());
    }

    @Test
    void deveTestarEqualsEHashCode() {
        Locatario locatario2 = new Locatario();
        locatario2.setId(1L);
        locatario2.setNome("Brunna Dornelles");
        locatario2.setSexo("Feminino");
        locatario2.setTelefone("51999999999");
        locatario2.setEmail("brunna@email.com");
        locatario2.setDataNascimento(LocalDate.of(1995, 8, 15));
        locatario2.setCpf("12345678901");
        locatario2.setAlugueis(locatario.getAlugueis());

        assertEquals(locatario, locatario2);
        assertEquals(locatario.hashCode(), locatario2.hashCode());
    }

    @Test
    void deveTestarToString() {
        String toString = locatario.toString();
        assertTrue(toString.contains("Brunna Dornelles"));
        assertTrue(toString.contains("12345678901"));
        assertTrue(toString.contains("brunna@email.com"));
    }
}
