package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroCidadeService {

	public static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe um cadastro de cidade com código %d";
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = cadastroEstadoService.buscarOuFalhar(estadoId);
		cidade.setEstado(estado);
		return cidadeRepository.save(cidade);
	}
	
	public void excluir(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
				String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format("Cidade de código %d não pode ser removida, pois está em uso", cidadeId));
		}
	}

    public List<Cidade> findAll() {
		return cidadeRepository.findAll();
    }

	public Cidade buscarPorID(Long cidadeId) {
		return cidadeRepository.findById(cidadeId).orElseThrow(() -> new
				EntidadeNaoEncontradaException(String.format(MSG_CIDADE_NAO_ENCONTRADA,cidadeId)));
	}
}
