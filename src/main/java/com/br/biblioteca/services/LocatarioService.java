package com.br.biblioteca.services;

import com.br.biblioteca.exceptions.LocatarioException;
import com.br.biblioteca.models.Aluguel;
import com.br.biblioteca.models.Locatario;
import com.br.biblioteca.repositories.AluguelRepository;
import com.br.biblioteca.repositories.LocatarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocatarioService {

    private final LocatarioRepository locatarioRepository;
    private final AluguelRepository aluguelRepository;

    public Locatario salvar(Locatario locatario) {
        if (locatarioRepository.existsByCpf(locatario.getCpf())) {
            throw new LocatarioException("Já existe um locatário cadastrado com este CPF.");
        }
        if (locatarioRepository.existsByEmail(locatario.getEmail())) {
            throw new LocatarioException("Já existe um locatário cadastrado com este e-mail.");
        }

        return locatarioRepository.save(locatario);
    }

    public Locatario buscarPorId(Long id) {
        return locatarioRepository.findById(id)
                .orElseThrow(() -> new LocatarioException("Locatário não encontrado com o ID: " + id));
    }

    public List<Locatario> listarTodos() {
        return locatarioRepository.findAll();
    }

    public Locatario atualizar(Long id, Locatario locatarioAtualizado) {
        Locatario existente = buscarPorId(id);

        existente.setNome(locatarioAtualizado.getNome());
        existente.setCpf(locatarioAtualizado.getCpf());
        existente.setEmail(locatarioAtualizado.getEmail());
        existente.setTelefone(locatarioAtualizado.getTelefone());

        return locatarioRepository.save(existente);
    }

    public void deletarLocatario(Long id) {
        Locatario locatario = buscarPorId(id);

        List<Aluguel> alugueis = aluguelRepository.findByLocatario_Id(id);

        boolean possuiLivroPendente = alugueis.stream()
                .anyMatch(aluguel -> aluguel.getDataDevolucao() == null);

        if (possuiLivroPendente) {
            throw new LocatarioException("O locatário não pode ser excluído, pois possui livros para devolução.");
        }

        locatarioRepository.delete(locatario);
    }
}


