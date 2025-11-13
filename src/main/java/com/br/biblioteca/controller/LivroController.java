package com.br.biblioteca.controller;

import com.br.biblioteca.models.Livro;
import com.br.biblioteca.services.LivroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    public ResponseEntity<Livro> salvar(@RequestBody Livro livro) {
        return ResponseEntity.ok(livroService.salvar(livro));
    }

    @GetMapping
    public ResponseEntity<List<Livro>> listarTodos() {
        return ResponseEntity.ok(livroService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizar(@PathVariable Long id, @RequestBody Livro livroAtualizado) {
        return ResponseEntity.ok(livroService.atualizar(id, livroAtualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        livroService.deletar(id);
        return ResponseEntity.ok("Livro removido com sucesso!");
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<Livro>> listarDisponiveis() {
        return ResponseEntity.ok(livroService.listarDisponiveis());
    }

    @GetMapping("/alugados")
    public ResponseEntity<List<Livro>> listarAlugados() {
        return ResponseEntity.ok(livroService.listarAlugados());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Livro>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(livroService.buscarPorNome(nome));
    }

    @GetMapping("/autor/{autorId}")
    public ResponseEntity<List<Livro>> buscarPorAutor(@PathVariable Long autorId) {
        return ResponseEntity.ok(livroService.buscarLivroPorAutor(autorId));
    }
}
