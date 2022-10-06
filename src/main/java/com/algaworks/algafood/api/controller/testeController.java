package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.infrastructi.repository.spec.RestauranresSpecs;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes")
public class testeController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @GetMapping("teste")
    public List<Restaurante> restauranteComFreteGratis(String nome){

        return  restauranteRepository.findComFreteGratis(nome);
    }
}
