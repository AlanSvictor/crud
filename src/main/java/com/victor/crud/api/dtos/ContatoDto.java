package com.victor.crud.api.dtos;

import javax.validation.constraints.NotEmpty;

import com.victor.crud.api.entities.Contato;

public class ContatoDto {

	private Long id;
	private String nome;
	
	public ContatoDto(String nome) {
		this.nome = nome;
	}
	
	public ContatoDto() {		
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotEmpty(message = "Nome não pode ser vazio.")
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return "ContatoDto [id=" + id + ", nome=" + this.nome + ", pessoa=" + "]";
	}

	public Contato toEntity() {
		Contato contato = new Contato();
		contato.setNome(this.nome);		
		return contato;
	}
	
	public static ContatoDto buildDto(Contato contato){
		ContatoDto contatoDto = new ContatoDto();
		contatoDto.setId(contato.getId());
		contatoDto.setNome(contato.getNome());
		return contatoDto;
	}	
	
}
