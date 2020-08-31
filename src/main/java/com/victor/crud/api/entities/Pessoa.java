package com.victor.crud.api.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.victor.crud.api.dtos.PessoaDto;

@Entity
@Table(name = "pessoa")
public class Pessoa {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "RG", nullable = false)
	private String rg;
	
	@Column(name = "data_nascimento", nullable = false)
	private LocalDate dataNascimento;
	
	@Column(name = "data_criacao", nullable = false)
	private LocalDateTime dataCriacao;
	
	@Column(name = "data_atualizacao", nullable = false)
	private LocalDateTime dataAtualizacao;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Contato> contatos;
	
	public Pessoa() {		
	}
	
	public Long getId() {
		return id;
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
	
	
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	
	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}
	
	public List<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}

	@PreUpdate
    public void preUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }
     
    @PrePersist
    public void prePersist() {
        final var atual = LocalDateTime.now();
        dataCriacao = atual;
        dataAtualizacao = atual;
    }
	
	@Override
	public String toString() {
		return "Pessoa [id=" + this.id + ", nome=" + this.nome + ", RG=" + this.rg + ", dataNascimento=" + this.dataNascimento + ", dataCriacao=" + this.dataCriacao 
				+ ", dataAtualizacao" + this.dataAtualizacao + "]";
	}
	
	public void atualizarDadosPessoa(PessoaDto pessoaDto){
		setNome(pessoaDto.getNome());
		setRg(pessoaDto.getRg());
		setDataNascimento(pessoaDto.getDataNascimentoConvertida());
		setContatos(pessoaDto.getContatosDto().stream().map(c -> c.toEntity()).collect(Collectors.toList()));

	}
}
