package com.br.biblioteca.controller;

import com.br.biblioteca.models.Aluguel;
import com.br.biblioteca.services.AluguelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/alugueis")
public class AluguelController {

    private final AluguelService aluguelService;

    @PostMapping
    public ResponseEntity<Aluguel> salvar(@RequestBody Aluguel aluguel) {
        return ResponseEntity.ok(aluguelService.salvar(aluguel));
    }

    @GetMapping
    public ResponseEntity<List<Aluguel>> listarTodos() {
        return ResponseEntity.ok(aluguelService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluguel> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(aluguelService.buscarPorId(id));
    }

    @GetMapping("/locatario/{locatarioId}")
    public ResponseEntity<List<Aluguel>> listarPorLocatario(@PathVariable Long locatarioId) {
        return ResponseEntity.ok(aluguelService.listarPorLocatario(locatarioId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluguel> atualizar(@PathVariable Long id, @RequestBody Aluguel dadosAtualizados) {
        return ResponseEntity.ok(aluguelService.atualizar(id, dadosAtualizados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        aluguelService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
