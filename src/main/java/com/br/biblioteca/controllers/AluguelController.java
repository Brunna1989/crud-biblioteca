package com.br.biblioteca.controllers;

import com.br.biblioteca.dtos.*;
import com.br.biblioteca.services.AluguelService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/biblioteca")
@RequiredArgsConstructor
public class AluguelController {

    private final AluguelService aluguelService;

    @PostMapping("/autores")
    public ResponseEntity<AutorDTO> criarAutor(@RequestBody AutorDTO dto) {
        return ResponseEntity.ok(aluguelService.criarAutor(dto));
    }

    @PutMapping("/autores/{id}")
    public ResponseEntity<AutorDTO> atualizarAutor(@PathVariable Long id, @RequestBody AutorDTO dto) {
        return ResponseEntity.ok(aluguelService.atualizarAutor(id, dto));
    }

    @GetMapping("/autores")
    public ResponseEntity<List<AutorDTO>> listarAutores() {
        return ResponseEntity.ok(aluguelService.listarAutores());
    }

    @GetMapping("/autores/nome/{nome}")
    public ResponseEntity<AutorDTO> buscarAutorPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(aluguelService.buscarAutorPorNome(nome));
    }

    @GetMapping("/autores/search")
    public ResponseEntity<AutorDTO> buscarAutorPorNomeParam(@RequestParam String nome) {
        return ResponseEntity.ok(aluguelService.buscarAutorPorNomeParam(nome));
    }

    @DeleteMapping("/autores/{id}")
    public ResponseEntity<Void> deletarAutor(@PathVariable Long id) {
        aluguelService.deletarAutor(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/livros")
    public ResponseEntity<LivroDTO> criarLivro(@RequestBody LivroDTO dto) {
        return ResponseEntity.ok(aluguelService.criarLivroComNovosAutores(dto));
    }

    @PutMapping("/livros/{id}")
    public ResponseEntity<LivroDTO> atualizarLivro(@PathVariable Long id, @RequestBody LivroDTO dto) {
        return ResponseEntity.ok(aluguelService.atualizarLivro(id, dto));
    }

    @GetMapping("/livros")
    public ResponseEntity<List<LivroDTO>> listarLivros() {
        return ResponseEntity.ok(aluguelService.listarLivros());
    }

    @GetMapping("/livros/{id}")
    public ResponseEntity<LivroDTO> buscarLivroPorId(@PathVariable Long id) {
        return ResponseEntity.ok(aluguelService.buscarLivroPorId(id));
    }

    @GetMapping("/livros/disponiveis")
    public ResponseEntity<List<LivroDTO>> listarLivrosDisponiveis() {
        return ResponseEntity.ok(aluguelService.listarLivrosDisponiveis());
    }

    @GetMapping("/livros/alugados")
    public ResponseEntity<List<LivroDTO>> listarLivrosAlugados() {
        return ResponseEntity.ok(aluguelService.listarLivrosAlugados());
    }

    @GetMapping("/livros/autor/{nomeAutor}")
    public ResponseEntity<List<LivroDTO>> listarLivrosPorAutor(@PathVariable String nomeAutor) {
        return ResponseEntity.ok(aluguelService.listarLivrosPorAutor(nomeAutor));
    }

    @DeleteMapping("/livros/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id) {
        aluguelService.deletarLivro(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/livros/search")
    public ResponseEntity<List<LivroDTO>> buscarLivrosPorNomeOuDisponibilidade(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Boolean disponivel) {
        return ResponseEntity.ok(aluguelService.buscarLivrosPorNomeOuDisponibilidade(nome, disponivel));
    }

    @PostMapping("/locatarios")
    public ResponseEntity<LocatarioDTO> criarLocatario(@RequestBody LocatarioDTO dto) {
        return ResponseEntity.ok(aluguelService.criarLocatario(dto));
    }

    @PutMapping("/locatarios/{id}")
    public ResponseEntity<LocatarioDTO> atualizarLocatario(@PathVariable Long id, @RequestBody LocatarioDTO dto) {
        return ResponseEntity.ok(aluguelService.atualizarLocatario(id, dto));
    }

    @GetMapping("/locatarios")
    public ResponseEntity<List<LocatarioDTO>> listarLocatarios() {
        return ResponseEntity.ok(aluguelService.listarLocatarios());
    }

    @DeleteMapping("/locatarios/{id}")
    public ResponseEntity<Void> deletarLocatario(@PathVariable Long id) {
        aluguelService.deletarLocatario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/locatarios/{id}/livros")
    public ResponseEntity<List<LivroDTO>> listarLivrosPorLocatario(@PathVariable Long id) {
        return ResponseEntity.ok(aluguelService.listarLivrosPorLocatario(id));
    }

    @PostMapping("/alugueis")
    public ResponseEntity<AluguelDTO> criarAluguel(@RequestBody AluguelDTO dto) {
        return ResponseEntity.ok(aluguelService.criarAluguel(dto));
    }

    @GetMapping("/alugueis")
    public ResponseEntity<List<AluguelDTO>> listarAlugueis() {
        return ResponseEntity.ok(aluguelService.listarAlugueis());
    }

    @PutMapping("/alugueis/{id}/finalizar")
    public ResponseEntity<Void> finalizarAluguel(@PathVariable Long id) {
        aluguelService.finalizarAluguel(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler({EntityNotFoundException.class, IllegalStateException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
