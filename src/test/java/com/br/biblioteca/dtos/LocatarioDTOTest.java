package com.br.biblioteca.dtos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LocatarioDTOTest {

    private LocatarioDTO locatarioDTO;

    @BeforeEach
    void setUp() {
        locatarioDTO = LocatarioDTO.builder()
                .id(1L)
                .nome("Brunna Dornelles")
                .sexo("Feminino")
                .telefone("(11) 99999-9999")
                .email("brunna@exemplo.com")
                .dataNascimento(LocalDate.of(1995, 8, 15))
                .cpf("123.456.789-00")
                .alugueisIds(List.of(10L, 20L))
                .build();
    }

    @Test
    @DisplayName("Deve criar LocatarioDTO corretamente com o builder")
    void deveCriarLocatarioDTOComBuilder() {
        assertNotNull(locatarioDTO);
        assertEquals(1L, locatarioDTO.getId());
        assertEquals("Brunna Dornelles", locatarioDTO.getNome());
        assertEquals("Feminino", locatarioDTO.getSexo());
        assertEquals("(11) 99999-9999", locatarioDTO.getTelefone());
        assertEquals("brunna@exemplo.com", locatarioDTO.getEmail());
        assertEquals(LocalDate.of(1995, 8, 15), locatarioDTO.getDataNascimento());
        assertEquals("123.456.789-00", locatarioDTO.getCpf());
        assertEquals(2, locatarioDTO.getAlugueisIds().size());
    }

    @Test
    @DisplayName("Deve permitir modificar valores via setters")
    void devePermitirModificarComSetters() {
        locatarioDTO.setId(2L);
        locatarioDTO.setNome("Ana Clara");
        locatarioDTO.setSexo("Feminino");
        locatarioDTO.setTelefone("(21) 98888-7777");
        locatarioDTO.setEmail("ana.clara@exemplo.com");
        locatarioDTO.setDataNascimento(LocalDate.of(2021, 4, 20));
        locatarioDTO.setCpf("987.654.321-00");
        locatarioDTO.setAlugueisIds(List.of(30L));

        assertEquals(2L, locatarioDTO.getId());
        assertEquals("Ana Clara", locatarioDTO.getNome());
        assertEquals("Feminino", locatarioDTO.getSexo());
        assertEquals("(21) 98888-7777", locatarioDTO.getTelefone());
        assertEquals("ana.clara@exemplo.com", locatarioDTO.getEmail());
        assertEquals(LocalDate.of(2021, 4, 20), locatarioDTO.getDataNascimento());
        assertEquals("987.654.321-00", locatarioDTO.getCpf());
        assertEquals(List.of(30L), locatarioDTO.getAlugueisIds());
    }

    @Test
    @DisplayName("Deve testar equals e hashCode corretamente")
    void deveTestarEqualsEHashCode() {
        LocatarioDTO outro = LocatarioDTO.builder()
                .id(1L)
                .nome("Brunna Dornelles")
                .sexo("Feminino")
                .telefone("(11) 99999-9999")
                .email("brunna@exemplo.com")
                .dataNascimento(LocalDate.of(1995, 8, 15))
                .cpf("123.456.789-00")
                .alugueisIds(List.of(10L, 20L))
                .build();

        assertEquals(locatarioDTO, outro);
        assertEquals(locatarioDTO.hashCode(), outro.hashCode());

        LocatarioDTO diferente = LocatarioDTO.builder()
                .id(99L)
                .nome("Outro Nome")
                .build();

        assertNotEquals(locatarioDTO, diferente);
        assertNotEquals(locatarioDTO.hashCode(), diferente.hashCode());
    }

    @Test
    @DisplayName("Deve testar toString gerado pelo Lombok")
    void deveTestarToString() {
        String texto = locatarioDTO.toString();
        assertTrue(texto.contains("LocatarioDTO"));
        assertTrue(texto.contains("Brunna Dornelles"));
        assertTrue(texto.contains("123.456.789-00"));
    }

    @Test
    @DisplayName("Deve testar construtor completo e construtor vazio")
    void deveTestarConstrutores() {
        LocatarioDTO vazio = new LocatarioDTO();
        assertNull(vazio.getId());
        assertNull(vazio.getNome());
        assertNull(vazio.getAlugueisIds());

        LocatarioDTO completo = new LocatarioDTO(
                3L,
                "João Silva",
                "Masculino",
                "(31) 97777-5555",
                "joao@exemplo.com",
                LocalDate.of(1990, 1, 10),
                "111.222.333-44",
                List.of(1L, 2L)
        );

        assertEquals(3L, completo.getId());
        assertEquals("João Silva", completo.getNome());
        assertEquals("Masculino", completo.getSexo());
        assertEquals("(31) 97777-5555", completo.getTelefone());
        assertEquals("joao@exemplo.com", completo.getEmail());
        assertEquals(LocalDate.of(1990, 1, 10), completo.getDataNascimento());
        assertEquals("111.222.333-44", completo.getCpf());
        assertEquals(List.of(1L, 2L), completo.getAlugueisIds());
    }
}
