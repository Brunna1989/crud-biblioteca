package com.br.biblioteca.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cpf")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    private String sexo;

    @NotNull(message = "O ano de nascimento é obrigatório")
    private Integer anoNascimento;

    @NotBlank(message = "O CPF é obrigatório")
    @Column(nullable = false, unique = true)
    private String cpf;

    @ManyToMany(mappedBy = "autores")
    @Builder.Default
    private List<Livro> livros = new ArrayList<>();
}

