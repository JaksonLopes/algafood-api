package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exeception.EntidadeEmUsoExeception;
import com.algaworks.algafood.domain.exeception.EntidadeNaoEncontradaExeception;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CadastroCozinhaService {

    @Autowired
   private CozinhaRepository cozinhaRepository;

    public Cozinha salvar (Cozinha cozinha){
        return cozinhaRepository.salvar(cozinha);
    }

    public void excluir (Long id){
        try {
            cozinhaRepository.remover(id);
        }catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaExeception(String.format("nao existe cozinha com o codigo %d ", id));
        }
        catch (DataIntegrityViolationException e) {
           throw new EntidadeEmUsoExeception(
                   String.format("Cozinha de codigo %d não pode ser removida, pois esta em uso", id)
           );
        }
    }
}
