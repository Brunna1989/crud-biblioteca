package com.br.biblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "autores", uniqueConstraints = {@UniqueConstraint(columnNames = "cpf")})
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @NotBlank
    private String nome;

    private String sexo;

    @NotBlank
    private String anoNascimento;

    @NotBlank
    @Size(min = 11, max=11)
    private String cpf;

    @ManyToMany(mappedBy = "autores")
    private Livro<Livro> livros = new ArrayList<>();

}
