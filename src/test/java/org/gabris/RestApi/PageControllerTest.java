package org.gabris.RestApi;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@WebMvcTest(PageController.class)
@DisabledInAotMode
public class PageControllerTest {
    @Autowired
    private PageController pageController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PageRepository repository;

    private Page page;

    @BeforeEach
    void setup() {
        page = new Page("/", List.of(HttpVerb.GET), "text/html", "pages/index.html");
        page.setId(1L);
        when(repository.findAll()).thenReturn(List.of(page));
        when(repository.findByPageName("/")).thenReturn(List.of(page));
        when(repository.findByPageName("unknown")).thenReturn(new ArrayList<>());
    }

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(pageController).isNotNull();
    }

    @Test
    void whenGetAllPages_andNoPageNameParam_thenRepositoryFindAllMethodCalled() throws Exception {
        mockMvc.perform(get("/pages")).andDo(print()).andExpect(status().isOk());
        verify(repository).findAll();
    }

    @Test
    void whenGetAllPages_andPageNameParamProvided_thenRepositoryFindByNameMethodCalled() throws Exception {
        mockMvc.perform(get("/pages?pageName=/")).andDo(print()).andExpect(status().isOk());
        verify(repository).findByPageName(eq("/"));
    }

    @Test
    void whenPostToPagesEndpoint_withAValidPage_thenRepositorySaveMethodIsCalled() throws Exception {
        mockMvc.perform(post("/pages").contentType("application/json").content("{\n" +
                "        \"id\": 1,\n" +
                "        \"pageName\": \"/\",\n" +
                "        \"verbs\": [\n" +
                "            \"GET\"\n" +
                "        ],\n" +
                "        \"contentType\": \"text/html\",\n" +
                "        \"path\": \"pages/index.html\"\n" +
                "    }"));
        verify(repository).save(any());
    }

    @Test
    void whenGetPagesIdEndpoint_thenRepositoryFindByIdMethodIsCalled() throws Exception {
        mockMvc.perform(get("/pages/1"));
        verify(repository).findById(1L);
    }

    @Test
    void whenGetPagesIdEndpoint_withUnknownPage_thenPageNotFoundExceptionThrown() throws Exception {
        when(repository.findById(2L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/pages/2")).andExpect(status().isNotFound());
    }



}