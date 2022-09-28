package com.algaworks.algafood.domain.exeception;

public class EntidadeNaoEncontradaExeception extends RuntimeException {

    public EntidadeNaoEncontradaExeception(String mensage){
        super(mensage);
    }

}
