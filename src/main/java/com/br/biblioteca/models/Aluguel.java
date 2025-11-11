package com.br.biblioteca.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aluguel {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    private Long id;

    @NotNull
    private LocalDate dataDevolucao = LocalDate.now().plusDays(2);

    @ManyToOne
    @JoinColumn(name = "locatario_id", nullable = false)
    private Locatario locatario;

    @ManyToMany
    @JoinTable(
            name = "aluguel_livro",
            joinColumns = @JoinColumn(name = "aluguel_id"),
            inverseJoinColumns = @JoinColumn(name = "livro_id")
    )
    private List<Livro> livros = new ArrayList<>();
}



