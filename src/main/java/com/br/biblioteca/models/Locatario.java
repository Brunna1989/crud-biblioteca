package com.br.biblioteca.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "locatarios", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cpf", "email"})
})
public class Locatario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    private String sexo;

    @NotBlank
    private String telefone;

    @Email
    @NotBlank
    private String email;

    @NotNull
    private LocalDate dataNascimento;

    @NotBlank
    @Size(min = 11, max = 11)
    private String cpf;

    @OneToMany(mappedBy = "locatario")
    private List<Aluguel> alugueis = new ArrayList<>();
}


// criação de endpoints para listar locatarios e buscar locatario por id
