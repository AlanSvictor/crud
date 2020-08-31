package com.victor.crud.api.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.victor.crud.api.dtos.PessoaDto;
import com.victor.crud.api.entities.Pessoa;
import com.victor.crud.api.repository.PessoaRepository;
import com.victor.crud.api.service.PessoaService;

@Service
public class PessoaServiceImpl implements PessoaService {
	
	private static final Logger log = LoggerFactory.getLogger(PessoaServiceImpl.class);
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Pessoa persistir(PessoaDto pessoaDto) {
		log.info("Persistindo pessoa: {}", pessoaDto);
		return this.pessoaRepository.save(pessoaDto.toEntity());
	}
	
	public Pessoa persistir(Pessoa pessoa) {
		log.info("Persistindo pessoa: {}", pessoa);
		return this.pessoaRepository.save(pessoa);
	}

	public Optional<Pessoa> buscarPorId(Long id) {
		log.info("Buscando pessoa pelo ID {}", id);
		return this.pessoaRepository.findById(id);
	}

	public Optional<Pessoa> buscarPorNome(String nome) {
		log.info("Buscando pessoa pelo nome {}", nome);
		return Optional.ofNullable(this.pessoaRepository.findByNome(nome));
		
	}

	public Optional<Pessoa> buscarPorRg(String rg) {
		log.info("Buscando a pessoa pelo RG");
		return Optional.ofNullable(this.pessoaRepository.findByRg(rg));		
	}
	
	public void removerPessoa(Long id){
		log.info("Removendo pessoa pelo id {}", id);
		this.pessoaRepository.deleteById(id);		
	}

}
