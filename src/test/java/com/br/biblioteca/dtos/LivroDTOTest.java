package com.br.biblioteca.dtos;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LivroDTOTest {

    @Test
    void deveCriarLivroDTOComBuilderEValidarCampos() {
        AutorDTO autor = AutorDTO.builder()
                .id(1L)
                .nome("Machado de Assis")
                .cpf("123.456.789-00")
                .sexo("Masculino")
                .anoNascimento(1839)
                .build();

        LivroDTO livro = LivroDTO.builder()
                .id(1L)
                .nome("Dom Casmurro")
                .isbn("9788535902779")
                .dataPublicacao(LocalDate.of(1899, 2, 1))
                .disponivel(true)
                .autores(List.of(autor))
                .autoresIds(List.of(1L))
                .build();

        assertThat(livro.getId()).isEqualTo(1L);
        assertThat(livro.getNome()).isEqualTo("Dom Casmurro");
        assertThat(livro.getIsbn()).isEqualTo("9788535902779");
        assertThat(livro.getDataPublicacao()).isEqualTo(LocalDate.of(1899, 2, 1));
        assertThat(livro.getDisponivel()).isTrue();
        assertThat(livro.getAutores()).hasSize(1);
        assertThat(livro.getAutores().get(0).getNome()).isEqualTo("Machado de Assis");
        assertThat(livro.getAutoresIds()).containsExactly(1L);
    }

    @Test
    void deveCompararLivrosComEqualsAndHashCode() {
        LivroDTO livro1 = LivroDTO.builder()
                .id(1L)
                .nome("Dom Casmurro")
                .isbn("9788535902779")
                .build();

        LivroDTO livro2 = LivroDTO.builder()
                .id(1L)
                .nome("Dom Casmurro")
                .isbn("9788535902779")
                .build();

        assertThat(livro1).isEqualTo(livro2);
        assertThat(livro1.hashCode()).isEqualTo(livro2.hashCode());
    }

    @Test
    void deveGerarToStringComInformacoesPrincipais() {
        LivroDTO livro = LivroDTO.builder()
                .id(2L)
                .nome("Memórias Póstumas de Brás Cubas")
                .isbn("9788572326975")
                .disponivel(false)
                .build();

        String toString = livro.toString();

        assertThat(toString).contains("Memórias Póstumas de Brás Cubas");
        assertThat(toString).contains("9788572326975");
        assertThat(toString).contains("false");
    }
}
