package com.br.biblioteca.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "autores", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cpf") // Garante CPF único no banco
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
}
