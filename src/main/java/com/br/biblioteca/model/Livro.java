package com.br.biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "livros", uniqueConstraints = {
        @UniqueConstraint(columnNames = "isbn")
})
public class Livro {

    @Id
    @GeneratedValue  (strategy = GenerationType.IDENTITY)

    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String isbn;

    @NotNull
    private LocalDate dataPublicacao;

    @ManyToMany
    @JoinTable(
            name = "livro_autor",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores = new ArrayList<>();

    @ManyToMany(mappedBy = "livros")
    private List<Aluguel> alugueis = new ArrayList<>();
}








