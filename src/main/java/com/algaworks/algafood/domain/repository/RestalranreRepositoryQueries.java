package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestalranreRepositoryQueries {
    List<Restaurante> find(String nome, BigDecimal taxaFrteInicial,
                           BigDecimal taxaFreteFinal);

    List<Restaurante> findComFreteGratis(String nome);
}
