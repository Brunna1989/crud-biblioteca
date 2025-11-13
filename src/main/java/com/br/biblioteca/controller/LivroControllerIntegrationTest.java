package com.br.biblioteca.controller;

import com.br.biblioteca.models.Autor;
import com.br.biblioteca.models.Livro;
import com.br.biblioteca.repositories.AutorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LivroControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AutorRepository autorRepository;

    private Autor autor;

    @BeforeEach
    void setup() {
        autor = new Autor();
        autor.setNome("Machado de Assis");
        autor.setSexo("M");
        autor.setAnoNascimento("1839");
        autor.setCpf("12345678901");
        autorRepository.save(autor);
    }

    @Test
    void deveTestarTodosOsEndpointsDeLivro() throws Exception {

        Livro livro = new Livro();
        livro.setNome("Dom Casmurro");
        livro.setIsbn("123456789");
        livro.setDataPublicacao(LocalDate.of(1899, 1, 1));
        livro.getAutores().add(autor);

        String json = objectMapper.writeValueAsString(livro);

        mockMvc.perform(post("/api/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/livros"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/livros/1"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/livros/1"))
                .andExpect(status().isOk());
    }
}
