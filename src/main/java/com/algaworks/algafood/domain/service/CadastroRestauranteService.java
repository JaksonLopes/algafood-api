package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CadastroRestauranteService {

    public static final String MSG_NAO_EXISTE_RESTAURANTE = "Não existe cadastro de reastaurante com código %d";
    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinhaService;

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cadastroCozinhaService.buscarOuFalar(cozinhaId);
        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }

    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    public Restaurante busrcarPorID(Long restauranteId) {
       return restauranteRepository.findById(restauranteId).orElseThrow(() -> new
               RestauranteNaoEncontradaException(restauranteId));

    }

    public List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return  restauranteRepository.findByTaxaFreteBetween(taxaInicial,taxaFinal);
    }

    public List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long id) {
        return  restauranteRepository.findByNomeContainingAndCozinhaId(nome,id);
    }
}
