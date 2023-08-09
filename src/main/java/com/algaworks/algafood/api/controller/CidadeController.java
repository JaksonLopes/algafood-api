package com.algaworks.algafood.api.controller;

import java.util.List;

import com.algaworks.algafood.domain.exception.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CadastroCidadeService cadastroCidade;

    @GetMapping
    public List<Cidade> listar() {
        return cadastroCidade.findAll();
    }

    @GetMapping("/{cidadeId}")
    public Cidade buscar(@PathVariable Long cidadeId) {
        return cadastroCidade.buscarPorID(cidadeId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Cidade adicionar(@RequestBody Cidade cidade) {
        try {
            return cadastroCidade.salvar(cidade);
        } catch (EstadoNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{cidadeId}")
    public Cidade atualizar(@PathVariable Long cidadeId,
                            @RequestBody Cidade cidade) {
        try {
            Cidade cidadeAtual = cadastroCidade.buscarPorID(cidadeId);
            BeanUtils.copyProperties(cidade, cidadeAtual, "id");
            return cadastroCidade.salvar(cidadeAtual);
        } catch (CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cidadeId) {
        cadastroCidade.excluir(cidadeId);
    }



}
