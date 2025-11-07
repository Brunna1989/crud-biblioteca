package com.br.biblioteca.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "alugueis")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A data de retirada é obrigatória")
    @Column(name = "data_retirada", nullable = false)
    private LocalDate dataRetirada;

    @Column(name = "data_devolucao")
    private LocalDate dataDevolucao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "locatario_id", nullable = false)
    private Locatario locatario;

    @ManyToMany
    @JoinTable(
            name = "aluguel_livro",
            joinColumns = @JoinColumn(name = "aluguel_id"),
            inverseJoinColumns = @JoinColumn(name = "livro_id")
    )
    @Builder.Default
    private List<Livro> livros = new ArrayList<>();

    public void emprestarLivro(Livro livro) {
        if (!livro.isDisponivel()) {
            throw new IllegalStateException("O livro '" + livro.getNome() + "' já está alugado e não pode ser emprestado novamente.");
        }
        livro.setDisponivel(false);
        this.livros.add(livro);
    }
}

