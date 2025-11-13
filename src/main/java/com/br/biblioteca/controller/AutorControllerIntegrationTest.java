package com.br.biblioteca.controller;

import com.br.biblioteca.models.Autor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AutorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveTestarTodosOsEndpointsDoAutor() throws Exception {

        Autor autor = new Autor();
        autor.setNome("Machado de Assis");
        autor.setSexo("M");
        autor.setAnoNascimento("1839");
        autor.setCpf("12345678901");

        String json = objectMapper.writeValueAsString(autor);

        mockMvc.perform(post("/api/autor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/autor"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/autor/1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/autor/buscar")
                        .param("nome", "Machado"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/autor/1"))
                .andExpect(status().isOk());
    }
}
