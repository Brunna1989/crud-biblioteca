package com.br.biblioteca.dtos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AutorDTOTest {

    private AutorDTO autorDTO;

    @BeforeEach
    void setUp() {
        autorDTO = AutorDTO.builder()
                .id(1L)
                .nome("Machado de Assis")
                .sexo("Masculino")
                .anoNascimento(1839)
                .cpf("123.456.789-00")
                .livrosIds(List.of(10L, 20L))
                .build();
    }

    @Test
    @DisplayName("Deve criar um AutorDTO corretamente com o builder")
    void deveCriarAutorDTOComBuilder() {
        assertNotNull(autorDTO);
        assertEquals(1L, autorDTO.getId());
        assertEquals("Machado de Assis", autorDTO.getNome());
        assertEquals("Masculino", autorDTO.getSexo());
        assertEquals(1839, autorDTO.getAnoNascimento());
        assertEquals("123.456.789-00", autorDTO.getCpf());
        assertEquals(2, autorDTO.getLivrosIds().size());
        assertTrue(autorDTO.getLivrosIds().contains(10L));
    }

    @Test
    @DisplayName("Deve permitir modificar os valores via setters")
    void devePermitirModificarComSetters() {
        autorDTO.setId(2L);
        autorDTO.setNome("Clarice Lispector");
        autorDTO.setSexo("Feminino");
        autorDTO.setAnoNascimento(1920);
        autorDTO.setCpf("987.654.321-00");
        autorDTO.setLivrosIds(List.of(30L, 40L));

        assertEquals(2L, autorDTO.getId());
        assertEquals("Clarice Lispector", autorDTO.getNome());
        assertEquals("Feminino", autorDTO.getSexo());
        assertEquals(1920, autorDTO.getAnoNascimento());
        assertEquals("987.654.321-00", autorDTO.getCpf());
        assertEquals(List.of(30L, 40L), autorDTO.getLivrosIds());
    }

    @Test
    @DisplayName("Deve testar equals e hashCode corretamente")
    void deveTestarEqualsEHashCode() {
        AutorDTO outro = AutorDTO.builder()
                .id(1L)
                .nome("Machado de Assis")
                .sexo("Masculino")
                .anoNascimento(1839)
                .cpf("123.456.789-00")
                .livrosIds(List.of(10L, 20L))
                .build();

        assertEquals(autorDTO, outro);
        assertEquals(autorDTO.hashCode(), outro.hashCode());

        AutorDTO diferente = AutorDTO.builder()
                .id(99L)
                .nome("Outro Autor")
                .build();

        assertNotEquals(autorDTO, diferente);
        assertNotEquals(autorDTO.hashCode(), diferente.hashCode());
    }

    @Test
    @DisplayName("Deve testar o método toString gerado pelo Lombok")
    void deveTestarToString() {
        String texto = autorDTO.toString();
        assertTrue(texto.contains("AutorDTO"));
        assertTrue(texto.contains("Machado de Assis"));
        assertTrue(texto.contains("Masculino"));
        assertTrue(texto.contains("123.456.789-00"));
    }

    @Test
    @DisplayName("Deve testar construtor com todos os argumentos e sem argumentos")
    void deveTestarConstrutores() {
        AutorDTO dtoVazio = new AutorDTO();
        assertNull(dtoVazio.getId());
        assertNull(dtoVazio.getNome());
        assertNull(dtoVazio.getLivrosIds());

        AutorDTO dtoCompleto = new AutorDTO(
                5L,
                "José de Alencar",
                "Masculino",
                1829,
                "111.222.333-44",
                List.of(55L)
        );

        assertEquals(5L, dtoCompleto.getId());
        assertEquals("José de Alencar", dtoCompleto.getNome());
        assertEquals("Masculino", dtoCompleto.getSexo());
        assertEquals(1829, dtoCompleto.getAnoNascimento());
        assertEquals("111.222.333-44", dtoCompleto.getCpf());
        assertEquals(List.of(55L), dtoCompleto.getLivrosIds());
    }
}
