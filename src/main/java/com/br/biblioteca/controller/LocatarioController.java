package com.br.biblioteca.controller;

import com.br.biblioteca.models.Locatario;
import com.br.biblioteca.services.LocatarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
