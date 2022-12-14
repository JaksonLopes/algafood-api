package com.algaworks.algafood.domain.infrastructi.repository;

import com.algaworks.algafood.domain.infrastructi.repository.spec.RestauranresSpecs;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestalranreRepositoryQueries;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestalranreRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired @Lazy
    private RestauranteRepository restauranteRepository;

    @Override
    public List<Restaurante> find(String nome,
                                  BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        Root<Restaurante> root = criteria.from(Restaurante.class);

        var predicates = new ArrayList<Predicate>();

        if(StringUtils.hasText(nome)) {
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%")) ;
        }

        if(taxaFreteInicial != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFreteInicial"), "%" + taxaFreteInicial + "%"));
        }
        if (taxaFreteFinal != null) {
            predicates.add (builder.lessThanOrEqualTo(root.get("taxaFreteFinal"), "%" + taxaFreteFinal + "%"));
        }
        TypedQuery<Restaurante> query = manager.createQuery(criteria);

        criteria.where(predicates.toArray(new Predicate[0]));

        return query.getResultList();
    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        return  restauranteRepository.findAll(RestauranresSpecs.comFreteGratis().and(RestauranresSpecs.comNomeSmelhante(nome)));

    }

}
