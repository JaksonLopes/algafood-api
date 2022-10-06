package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, RestalranreRepositoryQueries , JpaSpecificationExecutor<Restaurante> {


    //TODO Between consulta entre dois valores
    List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaFrteInicial, BigDecimal taxafreteFinal);

    //TODO ContainingAnd consulta por dois parametros nome do restaurante ou id cozinha
    List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long id);

    //@Query("from Restaurante where nome like %:nome% and cozinha.id =:id")
    List<Restaurante> consultarPorNome(String nome, Long id);

    //TODO First consulta trazendo o primeiro q encontrar
    Optional<Restaurante> findFirstByNomeContaining(String nome);

    //TODO Top2 retorna os primeiros resgisto de acordo o parametro do top os dois primeiro
    List<Restaurante> findTop2ByNomeContaining(String nome);

    //TODO exists retorna boolean se achar algum registro
    Boolean existsByNome (String nome);

    //TODO count retorna quantos registro encontrou
    int countById(Long id);

}
