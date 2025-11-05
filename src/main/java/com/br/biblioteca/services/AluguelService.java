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

    public LivroDTO criarLivroComNovosAutores(LivroDTO dto) {
        Livro livro = livroMapper.toEntity(dto);
        livro.setAutores(new ArrayList<>());

        if (dto.getAutores() != null && !dto.getAutores().isEmpty()) {
            for (AutorDTO autorDTO : dto.getAutores()) {
                Autor autor;

                Optional<Autor> existente = autorRepository.findAll().stream()
                        .filter(a -> a.getCpf() != null && a.getCpf().equalsIgnoreCase(autorDTO.getCpf()))
                        .findFirst();

                if (existente.isPresent()) {
                    autor = existente.get();
                } else {
                    autor = autorMapper.toEntity(autorDTO);
                    autor.setLivros(new ArrayList<>());
                    autor = autorRepository.save(autor);
                }

                livro.getAutores().add(autor);

                if (autor.getLivros() == null) {
                    autor.setLivros(new ArrayList<>());
                }
                if (autor.getLivros().stream().noneMatch(l -> Objects.equals(l.getId(), livro.getId()))) {
                    autor.getLivros().add(livro);
                }
            }
        }

        Livro salvo = livroRepository.save(livro);

        for (Autor autor : salvo.getAutores()) {
            Autor autorManaged = autorRepository.findById(autor.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));
            if (autorManaged.getLivros().stream().noneMatch(l -> Objects.equals(l.getId(), salvo.getId()))) {
                autorManaged.getLivros().add(salvo);
                autorRepository.save(autorManaged);
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
        return livroMapper.toDto(livroRepository.save(livro));
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
        if (livro.getAutores() != null) {
            for (Autor autor : new ArrayList<>(livro.getAutores())) {
                autor.getLivros().remove(livro);
                autorRepository.save(autor);
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
