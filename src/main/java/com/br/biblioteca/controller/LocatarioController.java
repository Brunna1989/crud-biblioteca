package com.br.biblioteca.controller;

import com.br.biblioteca.models.Locatario;
import com.br.biblioteca.services.LocatarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/locatarios")
public class LocatarioController {

    private final LocatarioService locatarioService;

    @PostMapping
    public ResponseEntity<Locatario> salvar(@RequestBody Locatario locatario) {
        Locatario novoLocatario = locatarioService.salvar(locatario);
        return ResponseEntity.ok(novoLocatario);
    }

    @GetMapping
    public ResponseEntity<List<Locatario>> listarTodos() {
        return ResponseEntity.ok(locatarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Locatario> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(locatarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Locatario> atualizar(@PathVariable Long id, @RequestBody Locatario locatario) {
        Locatario atualizado = locatarioService.atualizar(id, locatario);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        locatarioService.deletarLocatario(id);
        return ResponseEntity.ok("Locatário removido com sucesso!");
    }

    @GetMapping("/buscar")
    public ResponseEntity<Locatario> buscarPorEmail(@RequestParam String email) {
        List<Locatario> lista = locatarioService.listarTodos();
        Locatario encontrado = lista.stream()
                .filter(l -> l.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Locatário não encontrado com o e-mail: " + email));
        return ResponseEntity.ok(encontrado);
    }
}
