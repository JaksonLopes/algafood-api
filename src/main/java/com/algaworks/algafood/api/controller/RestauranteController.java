package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @GetMapping
    public List<Restaurante> listar() {
        return cadastroRestaurante.listar();
    }

    @GetMapping("/{restauranteId}")
    public Restaurante buscar(@PathVariable Long restauranteId) {

        return cadastroRestaurante.busrcarPorID(restauranteId);
    }


    @PostMapping
    public Restaurante adicionar(@RequestBody Restaurante restaurante) {
        return cadastroRestaurante.salvar(restaurante);
    }

    @PutMapping("/{restauranteId}")
    public Restaurante atualizar(@PathVariable Long restauranteId,
                                 @RequestBody Restaurante restaurante) {
        Restaurante restauranteAtual = cadastroRestaurante.busrcarPorID(restauranteId);
        BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formaPagamentos", "endereco", "dataCadastro", "produtos");
        return cadastroRestaurante.salvar(restauranteAtual);
    }

    @PatchMapping("/{restauranteId}")
    public Restaurante atualizarParcial(@PathVariable Long restauranteId,
                                              @RequestBody Map<String, Object> campos) {
        Restaurante restauranteAtual = cadastroRestaurante.busrcarPorID(restauranteId);
        merge(campos, restauranteAtual);
        return atualizar(restauranteId, restauranteAtual);
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);


        dadosOrigem.forEach((nomePropiedade, valorpropiedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropiedade);
            field.setAccessible(true);
            System.out.println(nomePropiedade + "=" + valorpropiedade);

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });
    }

    @GetMapping("/taxa")
    public List<Restaurante> buscarRestauranteTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return cadastroRestaurante.findByTaxaFreteBetween(taxaInicial, taxaFinal);
    }

    @GetMapping("/nomeOuId")
    public List<Restaurante> buscarRestauranteNome(String nome, Long id) {
        return cadastroRestaurante.findByNomeContainingAndCozinhaId(nome, id);
    }


}
