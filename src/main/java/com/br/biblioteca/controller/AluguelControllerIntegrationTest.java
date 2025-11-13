package com.br.biblioteca.controller;

import com.br.biblioteca.models.Aluguel;
import com.br.biblioteca.services.AluguelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AluguelController.class)
public class AluguelControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AluguelService aluguelService;

    @Autowired
    private ObjectMapper objectMapper;

    private Aluguel aluguel1;
    private Aluguel aluguel2;

    @BeforeEach
    void setup() {
        aluguel1 = new Aluguel();
        aluguel1.setId(1L);

        aluguel2 = new Aluguel();
        aluguel2.setId(2L);
    }

    @Test
    void deveSalvarAluguel() throws Exception {
        when(aluguelService.salvar(any(Aluguel.class))).thenReturn(aluguel1);

        mockMvc.perform(post("/api/alugueis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aluguel1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deveListarTodosOsAlugueis() throws Exception {
        when(aluguelService.listarTodos()).thenReturn(List.of(aluguel1, aluguel2));

        mockMvc.perform(get("/api/alugueis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deveBuscarAluguelPorId() throws Exception {
        when(aluguelService.buscarPorId(1L)).thenReturn(aluguel1);

        mockMvc.perform(get("/api/alugueis/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deveListarPorLocatario() throws Exception {
        when(aluguelService.listarPorLocatario(1L)).thenReturn(List.of(aluguel1));

        mockMvc.perform(get("/api/alugueis/locatario/{locatarioId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void deveAtualizarAluguel() throws Exception {
        when(aluguelService.atualizar(eq(1L), any(Aluguel.class))).thenReturn(aluguel1);

        mockMvc.perform(put("/api/alugueis/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(aluguel1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void deveDeletarAluguel() throws Exception {
        doNothing().when(aluguelService).deletar(1L);

        mockMvc.perform(delete("/api/alugueis/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
