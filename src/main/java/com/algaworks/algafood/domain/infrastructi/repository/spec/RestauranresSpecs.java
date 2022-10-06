package com.algaworks.algafood.domain.infrastructi.repository.spec;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestauranresSpecs {


    public static Specification<Restaurante> comFreteGratis(){

        return ((root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("taxaFrete"),
                        BigDecimal.ZERO));
    }
    public static Specification<Restaurante> comNomeSmelhante(String nome){
        return ((root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("nome"), "%" + nome + "%"));
    }

}
