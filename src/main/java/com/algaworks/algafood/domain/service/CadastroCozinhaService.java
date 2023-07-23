package com.algaworks.algafood.domain.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CadastroCozinhaService {

    public static final String MSG_COZINHA_NAO_ENCOTRADA = "Não existe um cadastro de cozinha com código %d";
    public static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";
    private final CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    public void excluir(Long cozinhaId) {
        try {
            cozinhaRepository.deleteById(cozinhaId);

        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format(MSG_COZINHA_NAO_ENCOTRADA, cozinhaId));

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_COZINHA_EM_USO, cozinhaId));
        }
    }

    public List<Cozinha> listar() {
        return cozinhaRepository.findAll();
    }

    public Cozinha buscarOuFalar(Long cozinhaId) {
        return cozinhaRepository.findById(cozinhaId).orElseThrow(()-> new EntidadeNaoEncontradaException(
                String.format(MSG_COZINHA_NAO_ENCOTRADA, cozinhaId)));
    }
}
