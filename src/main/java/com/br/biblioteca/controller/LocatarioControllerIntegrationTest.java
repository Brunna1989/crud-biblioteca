package com.br.biblioteca.controller;

import com.br.biblioteca.models.Locatario;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class LocatarioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveTestarTodosOsEndpointsDeLocatario() throws Exception {

        Locatario locatario = new Locatario();
        locatario.setNome("João da Silva");
        locatario.setCpf("12345678900");
        locatario.setTelefone("11999999999");
        locatario.setEmail("joao@email.com");
        locatario.setDataNascimento(LocalDate.of(1990, 5, 15)); // ✅ Campo obrigatório

        String json = objectMapper.writeValueAsString(locatario);

        mockMvc.perform(post("/locatarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João da Silva"));

        mockMvc.perform(get("/locatarios"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/locatarios/1"))
                .andExpect(status().isOk());

        locatario.setNome("João Silva Atualizado");
        String jsonAtualizado = objectMapper.writeValueAsString(locatario);

        mockMvc.perform(put("/locatarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonAtualizado))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Silva Atualizado"));

        mockMvc.perform(get("/locatarios/buscar")
                        .param("email", "joao@email.com"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/locatarios/1"))
                .andExpect(status().isOk());
    }
}
