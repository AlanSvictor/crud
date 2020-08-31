package com.victor.crud.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.victor.crud.api.entities.Pessoa;

@Transactional(readOnly = true)
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	Optional<Pessoa> findById(Long id);
	
	Pessoa findByNome(String nome);
	
	Pessoa findByRg(String rg);
}
