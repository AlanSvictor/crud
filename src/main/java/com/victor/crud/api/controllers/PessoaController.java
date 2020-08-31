package com.victor.crud.api.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.victor.crud.api.dtos.PessoaDto;
import com.victor.crud.api.entities.Pessoa;
import com.victor.crud.api.service.PessoaService;

@RestController
@RequestMapping("/api/pessoas")
@CrossOrigin(origins = "*")
public class PessoaController {
	
	private static final Logger log = LoggerFactory.getLogger(PessoaController.class);
	
	@Autowired
	private PessoaService pessoaService;
	
	public PessoaController() {
	}
	
	@PostMapping
	public ResponseEntity<PessoaDto> post(@Valid @RequestBody PessoaDto pessoaDto){
		log.info("Criando pessoa: {}", pessoaDto.toString());

		Pessoa pessoa = pessoaService.persistir(pessoaDto);
		
		return ResponseEntity.ok(PessoaDto.buildDto(pessoa));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<PessoaDto> get(@PathVariable("id") Long id){
		
		
		Optional<Pessoa> pessoa = this.pessoaService.buscarPorId(id);
		
		
		if (!pessoa.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(PessoaDto.buildDto(pessoa.get()));		
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<PessoaDto> remover(@PathVariable("id") Long id) {
		log.info("Removendo pessoa: {}", id);		
		this.pessoaService.removerPessoa(id);
		return ResponseEntity.ok().build();		
	}
	
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<PessoaDto> atualizar(@PathVariable("id") Long id,
			@Valid @RequestBody PessoaDto pessoaDto){
		log.info("Atualizando pessoa: {}", pessoaDto.toString());
				
		Optional<Pessoa> pessoaOptional = this.pessoaService.buscarPorId(id);
		if (!pessoaOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		pessoaOptional.get().atualizarDadosPessoa(pessoaDto);
		Pessoa pessoa = this.pessoaService.persistir(pessoaOptional.get());
		return ResponseEntity.ok(PessoaDto.buildDto(pessoa));
	}
	
	
}
