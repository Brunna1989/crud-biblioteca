package com.br.biblioteca.dtos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LivroDTOTest {

    private LivroDTO livroDTO;
    private AutorDTO autor1;
    private AutorDTO autor2;

    @BeforeEach
    void setUp() {
        autor1 = AutorDTO.builder()
                .id(1L)
                .nome("Machado de Assis")
                .sexo("Masculino")
                .anoNascimento(1839)
                .cpf("123.456.789-00")
                .livrosIds(List.of(10L))
                .build();

        autor2 = AutorDTO.builder()
                .id(2L)
                .nome("José de Alencar")
                .sexo("Masculino")
                .anoNascimento(1829)
                .cpf("987.654.321-00")
                .livrosIds(List.of(20L))
                .build();

        livroDTO = LivroDTO.builder()
                .id(1L)
                .nome("Dom Casmurro")
                .isbn("978-85-359-0277-0")
                .dataPublicacao(LocalDate.of(1899, 1, 1))
                .autores(List.of(autor1, autor2))
                .build();
    }

    @Test
    @DisplayName("Deve criar LivroDTO corretamente com o builder")
    void deveCriarLivroDTOComBuilder() {
        assertNotNull(livroDTO);
        assertEquals(1L, livroDTO.getId());
        assertEquals("Dom Casmurro", livroDTO.getNome());
        assertEquals("978-85-359-0277-0", livroDTO.getIsbn());
        assertEquals(LocalDate.of(1899, 1, 1), livroDTO.getDataPublicacao());
        assertEquals(2, livroDTO.getAutores().size());
        assertEquals("Machado de Assis", livroDTO.getAutores().get(0).getNome());
    }

    @Test
    @DisplayName("Deve permitir modificar os valores via setters")
    void devePermitirModificarComSetters() {
        livroDTO.setId(2L);
        livroDTO.setNome("Memórias Póstumas de Brás Cubas");
        livroDTO.setIsbn("978-85-359-0011-0");
        livroDTO.setDataPublicacao(LocalDate.of(1881, 3, 15));
        livroDTO.setAutores(List.of(autor1));

        assertEquals(2L, livroDTO.getId());
        assertEquals("Memórias Póstumas de Brás Cubas", livroDTO.getNome());
        assertEquals("978-85-359-0011-0", livroDTO.getIsbn());
        assertEquals(LocalDate.of(1881, 3, 15), livroDTO.getDataPublicacao());
        assertEquals(1, livroDTO.getAutores().size());
    }

    @Test
    @DisplayName("Deve testar equals e hashCode corretamente")
    void deveTestarEqualsEHashCode() {
        LivroDTO outro = LivroDTO.builder()
                .id(1L)
                .nome("Dom Casmurro")
                .isbn("978-85-359-0277-0")
                .dataPublicacao(LocalDate.of(1899, 1, 1))
                .autores(List.of(autor1, autor2))
                .build();

        assertEquals(livroDTO, outro);
        assertEquals(livroDTO.hashCode(), outro.hashCode());

        LivroDTO diferente = LivroDTO.builder()
                .id(99L)
                .nome("Outro Livro")
                .build();

        assertNotEquals(livroDTO, diferente);
        assertNotEquals(livroDTO.hashCode(), diferente.hashCode());
    }

    @Test
    @DisplayName("Deve testar toString gerado pelo Lombok")
    void deveTestarToString() {
        String texto = livroDTO.toString();
        assertTrue(texto.contains("LivroDTO"));
        assertTrue(texto.contains("Dom Casmurro"));
        assertTrue(texto.contains("978-85-359-0277-0"));
    }

    @Test
    @DisplayName("Deve testar construtor com todos os argumentos e construtor vazio")
    void deveTestarConstrutores() {
        LivroDTO dtoVazio = new LivroDTO();
        assertNull(dtoVazio.getId());
        assertNull(dtoVazio.getNome());
        assertNull(dtoVazio.getAutores());
        assertNull(dtoVazio.getAutoresIds());

        LivroDTO dtoCompleto = new LivroDTO(
                3L,
                "Iracema",
                "978-85-359-0001-1",
                LocalDate.of(1865, 5, 1),
                List.of(autor2),
                List.of(2L)
        );

        assertEquals(3L, dtoCompleto.getId());
        assertEquals("Iracema", dtoCompleto.getNome());
        assertEquals("978-85-359-0001-1", dtoCompleto.getIsbn());
        assertEquals(LocalDate.of(1865, 5, 1), dtoCompleto.getDataPublicacao());
        assertEquals(1, dtoCompleto.getAutores().size());
        assertEquals("José de Alencar", dtoCompleto.getAutores().get(0).getNome());
        assertEquals(List.of(2L), dtoCompleto.getAutoresIds());
    }
}

