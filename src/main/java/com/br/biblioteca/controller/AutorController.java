package com.br.biblioteca.controller;

import com.br.biblioteca.models.Autor;
import com.br.biblioteca.services.AutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ autor")
public class AutorController {

    private final AutorService autorService;

    @PostMapping
    public ResponseEntity<Autor> salvar(@RequestBody Autor autor) {
        return ResponseEntity.ok(autorService.salvarAutor(autor));
    }

    @GetMapping
    public ResponseEntity<List<Autor>> listarTodos() {
        return ResponseEntity.ok(autorService.listarTodosAutores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autor> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(autorService.buscarAutorPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Autor> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(autorService.buscaAutorPorNome(nome));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarAutor(@PathVariable Long id) {
        autorService.deletarAutor(id);
        return ResponseEntity.ok("Autor removido com sucesso!");
    }
}
