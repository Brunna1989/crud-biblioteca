package com.br.biblioteca.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(name = "Locatario", uniqueConstraints = {
        @UniqueConstraint(columnNames = "telefone")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Locatario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Column(nullable = true, length = 10)
    private String sexo;

    @NotBlank(message = "O telefone é obrigatório")
    @Column(nullable = false, unique = true)
    private String telefone;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail deve ser válido")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "A data de nascimento é obrigatória")
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotBlank(message = "O CPF é obrigatório")
    @Column(nullable = false, unique = true, length = 14)
    private String cpf;

    @OneToMany(mappedBy = "locatario")
    @Builder.Default
    private List<Aluguel> alugueis = new ArrayList<>();
}
