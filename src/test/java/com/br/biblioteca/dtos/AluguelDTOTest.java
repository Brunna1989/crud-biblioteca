package com.br.biblioteca.dtos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AluguelDTOTest {

    private AluguelDTO aluguelDTO;
    private LivroDTO livro1;
    private LivroDTO livro2;

    @BeforeEach
    void setUp() {
        livro1 = LivroDTO.builder()
                .id(1L)
                .nome("Dom Casmurro")
                .isbn("123456789")
                .dataPublicacao(LocalDate.of(1899, 1, 1))
                .build();

        livro2 = LivroDTO.builder()
                .id(2L)
                .nome("Memórias Póstumas de Brás Cubas")
                .isbn("987654321")
                .dataPublicacao(LocalDate.of(1881, 1, 1))
                .build();

        aluguelDTO = AluguelDTO.builder()
                .id(1L)
                .dataRetirada(LocalDate.of(2025, 11, 5))
                .dataDevolucao(LocalDate.of(2025, 11, 12))
                .locatarioId(10L)
                .livros(List.of(livro1, livro2))
                .build();
    }

    @Test
    @DisplayName("Deve criar um AluguelDTO corretamente com o builder")
    void deveCriarAluguelDTOComBuilder() {
        assertNotNull(aluguelDTO);
        assertEquals(1L, aluguelDTO.getId());
        assertEquals(LocalDate.of(2025, 11, 5), aluguelDTO.getDataRetirada());
        assertEquals(LocalDate.of(2025, 11, 12), aluguelDTO.getDataDevolucao());
        assertEquals(10L, aluguelDTO.getLocatarioId());
        assertEquals(2, aluguelDTO.getLivros().size());
        assertEquals("Dom Casmurro", aluguelDTO.getLivros().get(0).getNome());
    }

    @Test
    @DisplayName("Deve permitir modificar os valores via setters")
    void devePermitirSetters() {
        aluguelDTO.setId(2L);
        aluguelDTO.setDataRetirada(LocalDate.of(2025, 11, 6));
        aluguelDTO.setDataDevolucao(LocalDate.of(2025, 11, 13));
        aluguelDTO.setLocatarioId(20L);

        assertEquals(2L, aluguelDTO.getId());
        assertEquals(LocalDate.of(2025, 11, 6), aluguelDTO.getDataRetirada());
        assertEquals(LocalDate.of(2025, 11, 13), aluguelDTO.getDataDevolucao());
        assertEquals(20L, aluguelDTO.getLocatarioId());
    }

    @Test
    @DisplayName("Deve testar equals e hashCode corretamente")
    void deveTestarEqualsEHashCode() {
        AluguelDTO outro = AluguelDTO.builder()
                .id(1L)
                .dataRetirada(LocalDate.of(2025, 11, 5))
                .dataDevolucao(LocalDate.of(2025, 11, 12))
                .locatarioId(10L)
                .livros(List.of(livro1, livro2))
                .build();

        assertEquals(aluguelDTO, outro);
        assertEquals(aluguelDTO.hashCode(), outro.hashCode());
    }

    @Test
    @DisplayName("Deve testar toString gerado pelo Lombok")
    void deveTestarToString() {
        String texto = aluguelDTO.toString();
        assertTrue(texto.contains("AluguelDTO"));
        assertTrue(texto.contains("Dom Casmurro"));
        assertTrue(texto.contains("locatarioId=10"));
    }

    @Test
    @DisplayName("Deve criar objeto usando construtor completo e construtor vazio")
    void deveTestarConstrutores() {
        AluguelDTO dtoVazio = new AluguelDTO();
        assertNull(dtoVazio.getId());

        AluguelDTO dtoComArgs = new AluguelDTO(
                3L,
                LocalDate.of(2025, 11, 1),
                LocalDate.of(2025, 11, 8),
                5L,
                List.of(livro1)
        );

        assertEquals(3L, dtoComArgs.getId());
        assertEquals(5L, dtoComArgs.getLocatarioId());
        assertEquals(1, dtoComArgs.getLivros().size());
    }
}
