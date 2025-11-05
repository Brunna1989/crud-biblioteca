package com.br.biblioteca.services;

import com.br.biblioteca.dtos.*;
import com.br.biblioteca.entities.*;
import com.br.biblioteca.mappers.*;
import com.br.biblioteca.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AluguelService {

    private final AutorRepository autorRepository;
    private final LivroRepository livroRepository;
    private final LocatarioRepository locatarioRepository;
    private final AluguelRepository aluguelRepository;

    private final AutorMapper autorMapper;
    private final LivroMapper livroMapper;
    private final LocatarioMapper locatarioMapper;
    private final AluguelMapper aluguelMapper;

    public AutorDTO criarAutor(AutorDTO dto) {
        Autor autor = autorMapper.toEntity(dto);
        Autor salvo = autorRepository.save(autor);
        return autorMapper.toDto(salvo);
    }

    public AutorDTO atualizarAutor(Long id, AutorDTO dto) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));
        autor.setNome(dto.getNome());
        autor.setSexo(dto.getSexo());
        autor.setAnoNascimento(dto.getAnoNascimento());
        autor.setCpf(dto.getCpf());
        Autor salvo = autorRepository.save(autor);
        return autorMapper.toDto(salvo);
    }

    public List<AutorDTO> listarAutores() {
        return autorRepository.findAll().stream()
                .map(autorMapper::toDto)
                .collect(Collectors.toList());
    }

    public AutorDTO buscarAutorPorNome(String nome) {
        Autor autor = autorRepository.findAll().stream()
                .filter(a -> a.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado com nome: " + nome));
        return autorMapper.toDto(autor);
    }

    public void deletarAutor(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));
        if (autor.getLivros() != null && !autor.getLivros().isEmpty()) {
            throw new IllegalStateException("Autor possui livros associados e não pode ser deletado.");
        }
        autorRepository.delete(autor);
    }

    public LivroDTO criarLivro(LivroDTO dto) {
        Livro livro = livroMapper.toEntity(dto);

        if (dto.getAutores() != null && !dto.getAutores().isEmpty()) {
            List<Autor> autores = dto.getAutores().stream()
                    .map(aDto -> autorRepository.findById(aDto.getId())
                            .orElseThrow(() -> new EntityNotFoundException("Autor ID " + aDto.getId() + " não encontrado")))
                    .collect(Collectors.toList());

            livro.setAutores(new ArrayList<>(autores));

            for (Autor autor : autores) {
                if (autor.getLivros() == null) {
                    autor.setLivros(new ArrayList<>());
                }
                if (!autor.getLivros().contains(livro)) {
                    autor.getLivros().add(livro);
                }
            }
        }

        Livro salvo = livroRepository.save(livro);

        if (salvo.getAutores() != null && !salvo.getAutores().isEmpty()) {
            for (Autor autor : salvo.getAutores()) {
                Autor autorManaged = autorRepository.findById(autor.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Autor ID " + autor.getId() + " não encontrado após salvar livro"));
                if (autorManaged.getLivros() == null) autorManaged.setLivros(new ArrayList<>());
                if (autorManaged.getLivros().stream().noneMatch(l -> Objects.equals(l.getId(), salvo.getId()))) {
                    autorManaged.getLivros().add(salvo);
                    autorRepository.save(autorManaged);
                }
            }
        }

        return livroMapper.toDto(salvo);
    }

    public LivroDTO atualizarLivro(Long id, LivroDTO dto) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));

        livro.setNome(dto.getNome());
        livro.setIsbn(dto.getIsbn());
        livro.setDataPublicacao(dto.getDataPublicacao());

        if (dto.getAutores() != null) {
            if (livro.getAutores() != null) {
                for (Autor antigo : new ArrayList<>(livro.getAutores())) {
                    Autor a = autorRepository.findById(antigo.getId())
                            .orElseThrow(() -> new EntityNotFoundException("Autor ID " + antigo.getId() + " não encontrado"));
                    if (a.getLivros() != null) {
                        a.setLivros(a.getLivros().stream()
                                .filter(l -> !Objects.equals(l.getId(), livro.getId()))
                                .collect(Collectors.toList()));
                        autorRepository.save(a);
                    }
                }
            }

            List<Autor> novos = dto.getAutores().stream()
                    .map(aDto -> autorRepository.findById(aDto.getId())
                            .orElseThrow(() -> new EntityNotFoundException("Autor ID " + aDto.getId() + " não encontrado")))
                    .collect(Collectors.toList());
            livro.setAutores(new ArrayList<>(novos));

            for (Autor autor : novos) {
                Autor autorManaged = autorRepository.findById(autor.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Autor ID " + autor.getId() + " não encontrado"));
                if (autorManaged.getLivros() == null) autorManaged.setLivros(new ArrayList<>());
                if (autorManaged.getLivros().stream().noneMatch(l -> Objects.equals(l.getId(), livro.getId()))) {
                    autorManaged.getLivros().add(livro);
                    autorRepository.save(autorManaged);
                }
            }
        }

        Livro salvo = livroRepository.save(livro);
        return livroMapper.toDto(salvo);
    }

    public LivroDTO buscarLivroPorId(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
        return livroMapper.toDto(livro);
    }

    public List<LivroDTO> listarLivros() {
        return livroRepository.findAll().stream()
                .map(livroMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<LivroDTO> listarLivrosDisponiveis() {
        List<Livro> disponiveis = livroRepository.findAll().stream()
                .filter(livro -> livro.getAlugueis() == null ||
                        livro.getAlugueis().stream().allMatch(a -> a.getDataDevolucao() != null))
                .collect(Collectors.toList());
        return disponiveis.stream().map(livroMapper::toDto).collect(Collectors.toList());
    }

    public List<LivroDTO> listarLivrosAlugados() {
        List<Livro> alugados = livroRepository.findAll().stream()
                .filter(livro -> livro.getAlugueis() != null &&
                        livro.getAlugueis().stream().anyMatch(a -> a.getDataDevolucao() == null))
                .collect(Collectors.toList());
        return alugados.stream().map(livroMapper::toDto).collect(Collectors.toList());
    }

    public List<LivroDTO> listarLivrosPorAutor(String nomeAutor) {
        Autor autor = autorRepository.findAll().stream()
                .filter(a -> a.getNome().equalsIgnoreCase(nomeAutor))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));
        return autor.getLivros().stream()
                .map(livroMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deletarLivro(Long id) {
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado"));
        if (livro.getAlugueis() != null && !livro.getAlugueis().isEmpty()) {
            throw new IllegalStateException("Livro já foi alugado e não pode ser excluído.");
        }

        if (livro.getAutores() != null) {
            for (Autor autor : new ArrayList<>(livro.getAutores())) {
                Autor a = autorRepository.findById(autor.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Autor ID " + autor.getId() + " não encontrado"));
                if (a.getLivros() != null) {
                    a.setLivros(a.getLivros().stream()
                            .filter(l -> !Objects.equals(l.getId(), livro.getId()))
                            .collect(Collectors.toList()));
                    autorRepository.save(a);
                }
            }
        }

        livroRepository.delete(livro);
    }

    public LocatarioDTO criarLocatario(LocatarioDTO dto) {
        Locatario locatario = locatarioMapper.toEntity(dto);
        Locatario salvo = locatarioRepository.save(locatario);
        return locatarioMapper.toDto(salvo);
    }

    public List<LocatarioDTO> listarLocatarios() {
        return locatarioRepository.findAll().stream()
                .map(locatarioMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deletarLocatario(Long id) {
        Locatario locatario = locatarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Locatário não encontrado"));
        boolean possuiDevolucaoPendente = locatario.getAlugueis() != null &&
                locatario.getAlugueis().stream().anyMatch(a -> a.getDataDevolucao() == null);
        if (possuiDevolucaoPendente) {
            throw new IllegalStateException("Locatário possui livros a devolver e não pode ser removido.");
        }
        locatarioRepository.delete(locatario);
    }

    public List<LivroDTO> listarLivrosPorLocatario(Long locatarioId) {
        Locatario loc = locatarioRepository.findById(locatarioId)
                .orElseThrow(() -> new EntityNotFoundException("Locatário não encontrado"));
        return loc.getAlugueis().stream()
                .flatMap(a -> a.getLivros().stream())
                .distinct()
                .map(livroMapper::toDto)
                .collect(Collectors.toList());
    }

    public AluguelDTO criarAluguel(AluguelDTO dto) {
        if (dto.getLivros() == null || dto.getLivros().isEmpty()) {
            throw new IllegalArgumentException("É necessário informar ao menos um livro para o aluguel.");
        }

        Locatario locatario = locatarioRepository.findById(dto.getLocatarioId())
                .orElseThrow(() -> new EntityNotFoundException("Locatário não encontrado"));

        List<Livro> livros = dto.getLivros().stream()
                .map(l -> livroRepository.findById(l.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Livro ID " + l.getId() + " não encontrado")))
                .collect(Collectors.toList());

        Aluguel aluguel = Aluguel.builder()
                .dataRetirada(dto.getDataRetirada() != null ? dto.getDataRetirada() : LocalDate.now())
                .dataDevolucao(dto.getDataDevolucao() != null
                        ? dto.getDataDevolucao()
                        : LocalDate.now().plusDays(2))
                .locatario(locatario)
                .livros(new ArrayList<>(livros))
                .build();

        Aluguel salvo = aluguelRepository.save(aluguel);

        for (Livro livro : livros) {
            Livro livroManaged = livroRepository.findById(livro.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Livro ID " + livro.getId() + " não encontrado"));
            if (livroManaged.getAlugueis() == null) livroManaged.setAlugueis(new ArrayList<>());
            if (livroManaged.getAlugueis().stream().noneMatch(a -> Objects.equals(a.getId(), salvo.getId()))) {
                livroManaged.getAlugueis().add(salvo);
                livroRepository.save(livroManaged);
            }
        }

        return aluguelMapper.toDto(salvo);
    }

    public List<AluguelDTO> listarAlugueis() {
        return aluguelRepository.findAll().stream()
                .map(aluguelMapper::toDto)
                .collect(Collectors.toList());
    }

    public void finalizarAluguel(Long id) {
        Aluguel aluguel = aluguelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aluguel não encontrado"));
        aluguel.setDataDevolucao(LocalDate.now());
        aluguelRepository.save(aluguel);
    }
}
