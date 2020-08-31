package com.victor.crud.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.victor.crud.api.CrudApplication;
import com.victor.crud.api.dtos.ContatoDto;
import com.victor.crud.api.dtos.PessoaDto;
import com.victor.crud.api.entities.Pessoa;
import com.victor.crud.api.service.PessoaService;

@ContextConfiguration(classes = CrudApplication.class)
@SpringBootTest
public class PessoaControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mvc;

	@Autowired
	private PessoaService pessoaService;

	@BeforeEach
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void validarPost() throws Exception {

		PessoaDto pessoaDto = new PessoaDto();
		pessoaDto.setNome("PessoaTeste");
		pessoaDto.setRg("1232545");
		pessoaDto.setDataNascimento("16/05/2015");
		pessoaDto.setContatosDto(List.of(new ContatoDto("Rua de teste")));

		mvc.perform(MockMvcRequestBuilders.post("/api/pessoas")
				.content(asJsonString(pessoaDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		Optional<Pessoa> pessoa = this.pessoaService.buscarPorId(1L);
		Assert.assertTrue(pessoa.isPresent());
		Assert.assertEquals(pessoaDto.getNome(), pessoa.get().getNome());
	}
	
	@Test
	public void validarGet() throws Exception {

		PessoaDto pessoaDto = new PessoaDto();
		pessoaDto.setNome("PessoaTeste");
		pessoaDto.setRg("1232545");
		pessoaDto.setDataNascimento("16/05/2015");
		pessoaDto.setContatosDto(List.of(new ContatoDto("Rua de teste")));
		Pessoa pessoa = this.pessoaService.persistir(pessoaDto);

		mvc.perform(MockMvcRequestBuilders.get("/api/pessoas/"+ pessoa.getId())
				.accept(MediaType.APPLICATION_JSON))
	      		.andDo(print())
	      		.andExpect(status().isOk())
	      		.andExpect(MockMvcResultMatchers.jsonPath("$.rg").value(pessoaDto.getRg()));

	}
	
	@Test
	public void validarPut() throws Exception {

		PessoaDto pessoaDto = new PessoaDto();
		pessoaDto.setNome("PessoaTeste");
		pessoaDto.setRg("1232545");
		pessoaDto.setDataNascimento("16/05/2015");
		pessoaDto.setContatosDto(List.of(new ContatoDto("Rua de teste")));		

		mvc.perform(MockMvcRequestBuilders.put("/api/pessoas/"+ 1)
				.content(asJsonString(pessoaDto))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(pessoaDto.getNome()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.rg").value(pessoaDto.getRg()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.dataNascimento").value(pessoaDto.getDataNascimento()));
	}
	
	@Test
	public void validarDelete() throws Exception 
	{
		PessoaDto pessoaDto = new PessoaDto();
		pessoaDto.setNome("PessoaTeste");
		pessoaDto.setRg("1232545");
		pessoaDto.setDataNascimento("16/05/2015");
		pessoaDto.setContatosDto(List.of(new ContatoDto("Rua de teste")));
		Pessoa pessoa = this.pessoaService.persistir(pessoaDto);
				
		mvc.perform(MockMvcRequestBuilders.delete("/api/pessoas/"+ pessoa.getId()))
	        .andExpect(status().isOk());
	}
	
	

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
