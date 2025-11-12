package com.br.biblioteca.services;

import com.br.biblioteca.exceptions.AluguelException;
import com.br.biblioteca.exceptions.LivroException;
import com.br.biblioteca.models.Aluguel;
import com.br.biblioteca.models.Livro;
import com.br.biblioteca.models.Locatario;
import com.br.biblioteca.repositories.AluguelRepository;
import com.br.biblioteca.repositories.LivroRepository;
import com.br.biblioteca.repositories.LocatarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AluguelService {

    private final AluguelRepository aluguelRepository;
    private final LivroRepository livroRepository;
    private final LocatarioRepository locatarioRepository;

    public Aluguel salvar(Aluguel aluguel) {
        if (aluguel.getLocatario() == null || aluguel.getLocatario().getId() == null) {
            throw new AluguelException("É necessário informar um locatário válido para o aluguel.");
        }

        if (aluguel.getLivros() == null || aluguel.getLivros().isEmpty()) {
            throw new AluguelException("O aluguel deve conter ao menos um livro.");
        }

        Locatario locatario = locatarioRepository.findById(aluguel.getLocatario().getId())
                .orElseThrow(() -> new AluguelException("Locatário não encontrado."));

        aluguel.setLocatario(locatario);
        aluguel.setDataDevolucao(LocalDate.now().plusDays(2)); // devolução padrão em 2 dias

        for (Livro livro : aluguel.getLivros()) {
            Livro livroExistente = livroRepository.findById(livro.getId())
                    .orElseThrow(() -> new LivroException("Livro com ID " + livro.getId() + " não encontrado."));

            if (livroExistente.isAlugado()) {
                throw new LivroException("O livro '" + livroExistente.getNome() + "' já está alugado.");
            }

            livroExistente.setAlugado(true);
            livroRepository.save(livroExistente);
        }

        return aluguelRepository.save(aluguel);
    }

    public List<Aluguel> listarTodos() {
        return aluguelRepository.findAll();
    }

    public Aluguel buscarPorId(Long id) {
        return aluguelRepository.findById(id)
                .orElseThrow(() -> new AluguelException("Aluguel com ID " + id + " não encontrado."));
    }

    public List<Aluguel> listarPorLocatario(Long locatarioId) {
        if (!locatarioRepository.existsById(locatarioId)) {
            throw new AluguelException("Locatário com ID " + locatarioId + " não encontrado.");
        }

        return aluguelRepository.findByLocatario_Id(locatarioId);
    }

    public Aluguel atualizar(Long id, Aluguel dadosAtualizados) {
        Aluguel existente = buscarPorId(id);

        if (dadosAtualizados.getLivros() == null || dadosAtualizados.getLivros().isEmpty()) {
            throw new AluguelException("O aluguel deve conter ao menos um livro.");
        }

        existente.setLivros(dadosAtualizados.getLivros());
        existente.setDataDevolucao(dadosAtualizados.getDataDevolucao());

        return aluguelRepository.save(existente);
    }

    public void deletar(Long id) {
        Aluguel aluguel = buscarPorId(id);

        for (Livro livro : aluguel.getLivros()) {
            livro.setAlugado(false);
            livroRepository.save(livro);
        }

        aluguelRepository.delete(aluguel);
    }
}
