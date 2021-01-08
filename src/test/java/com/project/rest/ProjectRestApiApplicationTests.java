package com.project.rest;

import com.project.rest.*;
import com.project.rest.model.Projekt;
import com.project.rest.service.ProjektService;
import org.hibernate.criterion.Example;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
@Configuration
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin")
class ProjectRestApiApplicationTests {

	private final String apiPath = "http://localhost:8080/api/projekty";

	@MockBean
	private ProjektService mockProjektService; //tzw. mock (czyli obiekt, którego używa się zamiast rzeczywistej
	//implementacji) serwisu wykorzystywany przy testowaniu kontrolera
	@Autowired
	private MockMvc mockMvc;
	//private JacksonTester<Projekt> jacksonTester;


	@Test
	public void getProjekt() throws Exception {
		Projekt projekt = new Projekt(2, "Nazwa2", "Opis2", LocalDateTime.now(), LocalDate.of(2020, 6, 7));
		when(mockProjektService.getProjekt(projekt.getProjektId()))
				.thenReturn(Optional.of(projekt));
		mockMvc.perform(get(apiPath + "/{projektId}", projekt.getProjektId()).accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.projektId").value(projekt.getProjektId()))
				.andExpect(jsonPath("$.nazwa").value(projekt.getNazwa()));
		verify(mockProjektService, times(1)).getProjekt(projekt.getProjektId());
		verifyNoMoreInteractions(mockProjektService);
	}
