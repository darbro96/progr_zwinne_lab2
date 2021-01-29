package com.project.rest;

import com.project.rest.model.Projekt;
import com.project.rest.repository.ProjektRepository;
import com.project.rest.service.ProjektService;
import com.project.rest.service.ProjektServiceImpl;
import com.project.rest.service.SerwisTestowy;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
class ProjectRestApiApplicationTests {
	private final String apiPath = "https://localhost:8443/api/projekty/1";
	@MockBean
	private ProjektServiceImpl mockProjektService;

	@MockBean
	private SerwisTestowy serwisTestowy;

	//@InjectMocks
	//private ProjektServiceImpl mockProjektService;

	//@Mock
	//private ProjektRepository projektRepository;

	@Autowired
	private MockMvc mockMvc;

	private JacksonTester<Projekt> jacksonTester;
	//@Before()
	//public void init(){
	//	MockitoAnnotations.initMocks(this);
//	}



	@Test
	public void getProjekt() throws Exception {
		Projekt projekt = new Projekt(1, "Nazwa1", "Opis1", LocalDateTime.now(), LocalDate.of(2020, 6, 7));

		//mockProjektService.addProject(projekt);

		when(mockProjektService.getProjekt(projekt.getProjektId()))
				.thenReturn(Optional.of(projekt));

		mockMvc.perform(get(apiPath).contentType("application/json"))
				.andDo(print())
				.andExpect(status().isOk())
				//.andExpect(jsonPath("$.content[*]").exists()) //content[*] - oznacza całą zawartość tablicy content
				//.andExpect(jsonPath("$.content.length()").value(1))
				.andExpect(jsonPath("$.projektId").value(projekt.getProjektId()))
				.andExpect(jsonPath("$.nazwa").value(projekt.getNazwa()));

		//mockMvc.perform(get("https://localhost:8443/api/projekty/1"))
		//				.andExpect(status().isOk())
		//				.andExpect(content().contentType("application/json"))
		//				.andDo(print())
		//				.andExpect(jsonPath("$.projektId").value(1))
		//				.andExpect(status().isOk());
		//				//.andExpect(jsonPath("$.").exists());

		//mockMvc.perform(get(apiPath).contentType(MediaType.APPLICATION_JSON))
		//		.andDo(print())
		//		.andExpect(status().isOk())
		//		.andExpect(jsonPath("$.content[*]").exists()); //content[*] - oznacza całą zawartość tablicy content
				//.andExpect(jsonPath("$.content.length()").value(1))
				//.andExpect(jsonPath("$.content[0].projektId").value(projekt.getProjektId()))
				//.andExpect(jsonPath("$.content[0].nazwa").value(projekt.getNazwa()));

		verify(mockProjektService, times(1)).getProjekt(projekt.getProjektId());
		verifyNoMoreInteractions(mockProjektService);
	}

	/*@Test
	void getprojekty(){
		Projekt projekt = new Projekt();
		projekt.setProjektId(23);
		Page<Projekt> page = new PageImpl<>(Collections.singletonList(projekt));
		when(mockProjektService.getProjekty(any(Pageable.class))).thenReturn((List<Projekt>) page);

		when(projektRepository.findOne(mockProjektService.getProjekt(projekt.getProjektId())).thenReturn(Optional.of(projekt));

		Projekt proj = mockProjektService.getProjekt(23L);

		verify(projektRepository).findOne(23l);


	}*/
	@Test
	void contextLoads() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders
				.get(apiPath).accept(MediaType.APPLICATION_JSON)).andReturn();
		String content = result.getResponse().getContentAsString();
		System.out.println(content);

	}

}
