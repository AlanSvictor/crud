package com.victor.crud.api.service;

import java.util.Optional;

import com.victor.crud.api.dtos.PessoaDto;
import com.victor.crud.api.entities.Pessoa;

public interface PessoaService {
	
	Pessoa persistir(PessoaDto pessoaDto);
	
	Pessoa persistir(Pessoa pessoa);
	
	Optional<Pessoa> buscarPorId(Long id);

	Optional<Pessoa> buscarPorNome(String nome);
	
	Optional<Pessoa> buscarPorRg(String rg);
	
	void removerPessoa(Long id); 		
}
