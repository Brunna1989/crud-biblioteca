package com.br.biblioteca.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "livros", uniqueConstraints = {
        @UniqueConstraint(columnNames = "isbn")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do livro é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "O ISBN é obrigatório")
    @Column(nullable = false, unique = true)
    private String isbn;

    @NotNull(message = "A data de publicação é obrigatória")
    @Column(name = "data_publicacao", nullable = false)
    private LocalDate dataPublicacao;

    @ManyToMany
    @JoinTable(
            name = "livro_autor",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores;

    @ManyToMany(mappedBy = "livros")
    private List<Aluguel> alugueis;
}
