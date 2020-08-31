package com.victor.crud.api.dtos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.victor.crud.api.entities.Pessoa;



public class PessoaDto {

	private Long id;
	
	@NotEmpty(message = "Nome não pode ser vazio.")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
	private String nome;
	
	@NotEmpty(message = "RG não pode ser vazio.")
	@Length(min = 5, message = "RG deve conter no minimo 5 caracteres.")
	private String rg;
	
	@NotEmpty(message = "Data de nascimento não pode ser vazia.")
	private String dataNascimento;
	
	private List<ContatoDto> contatosDto;
	
	public PessoaDto() {
		
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	

	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	

	public String getRg() {
		return rg;
	}
	
	public void setRg(String rg) {
		this.rg = rg;
	}
	

	public String getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	@Override
	public String toString() {
		return "PessoaDto [id=" + this.id + ", nome=" + this.nome + "]";
	}
	
		
	public static PessoaDto buildDto(Pessoa pessoa) {
		PessoaDto pessoaDto = new PessoaDto();
		pessoaDto.setId(pessoa.getId());
		pessoaDto.setNome(pessoa.getNome());
		pessoaDto.setRg(pessoa.getRg());
		pessoaDto.setDataNascimento(pessoa.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		pessoaDto.setContatosDto(pessoa.getContatos().stream().map(c -> ContatoDto.buildDto(c)).collect(Collectors.toList()));
		return pessoaDto;
	}	
	
	public Pessoa toEntity() {
		Pessoa pessoa = new Pessoa();
		pessoa.setNome(this.nome);
		pessoa.setRg(this.rg);
		pessoa.setDataNascimento(getDataNascimentoConvertida());
		pessoa.setContatos(this.contatosDto.stream().map(c -> c.toEntity()).collect(Collectors.toList()));
		return pessoa;
	}

	public List<ContatoDto> getContatosDto() {
		return contatosDto;
	}

	public void setContatosDto(List<ContatoDto> contatoDto) {
		this.contatosDto = contatoDto;
	}
	
	@JsonIgnore
	public LocalDate getDataNascimentoConvertida() {
		return LocalDate.parse(this.dataNascimento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	
}
