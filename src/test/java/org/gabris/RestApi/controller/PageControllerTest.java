package org.gabris.RestApi.controller;

import org.assertj.core.api.Assertions;
import org.gabris.RestApi.controller.PageController;
import org.gabris.RestApi.data.PageRepository;
import org.gabris.RestApi.model.Page;
import org.gabris.RestApi.util.HttpVerb;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private static ObjectMapper objectMapper;

    private static final String CONTENT_TYPE = "application/json";


    @BeforeAll
    static void beforeAll() {
        objectMapper = new ObjectMapper();
    }

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
        mockMvc.perform(post("/pages").contentType(CONTENT_TYPE).content("""
                {
                        "id": 1,
                        "pageName": "/",
                        "verbs": [
                            "GET"
                        ],
                        "contentType": "text/html",
                        "path": "pages/index.html"
                    }"""));
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

    @Test
    void whenCompletePageIsSentToPutPageEndpoint_andPageDoesNotAlreadyExist_ThenRepositorySaveMethodIsCalled() throws Exception {
        when(repository.findById(eq(1L))).thenReturn(Optional.empty());
        mockMvc.perform(put("/pages/1").contentType(CONTENT_TYPE).content(objectMapper.writeValueAsString(page)));
        verify(repository).save(eq(page));
    }

    @Test
    void whenACompleteNewPageIsSentToPutPageEndpoint_andPageDoesExist_ThenRepositorySaveMethodIsCalledWithNewPage() throws Exception {
        when(repository.findById(eq(1L))).thenReturn(Optional.of(page));
        Page newPage = new Page("/new", List.of(HttpVerb.PUT), "application/json", "pages/newPage.html");
        newPage.setId(1L);
        mockMvc.perform(put("/pages/1").contentType(CONTENT_TYPE).content(objectMapper.writeValueAsString(newPage)));
        verify(repository).save(eq(newPage));
    }

    @Test
    void whenAPartialNewPageIsSentToPutPageEndpoint_andPageDoesExist_ThenRepositorySaveMethodIsCalledWithNewPage() throws Exception {
        when(repository.findById(eq(1L))).thenReturn(Optional.of(page));
        Page newPage = new Page("/new", null, null, null);
        newPage.setId(1L);
        mockMvc.perform(put("/pages/1").contentType(CONTENT_TYPE).content(objectMapper.writeValueAsString(newPage)));
        Page updatedPage = new Page(newPage.getPageName(), page.getVerbs(), page.getContentType(), page.getPath());
        updatedPage.setId(1L);
        verify(repository).save(eq(updatedPage));
    }

    @Test
    void whenAPartialNewPageIsSentToPutPageEndpoint_andPageDoesNotExist_ThenRepositorySaveMethodIsCalledWithPartialPage() throws Exception {
        when(repository.findById(eq(1L))).thenReturn(Optional.empty());
        Page newPage = new Page("/new", null, null, null);
        newPage.setId(1L);
        mockMvc.perform(put("/pages/1").contentType(CONTENT_TYPE).content(objectMapper.writeValueAsString(newPage)));
        verify(repository).save(eq(newPage));
    }

    @Test
    void whenDeleteEndpointCalled_thenRepositoryDeleteByIdMethodIsCalled() throws Exception {
        mockMvc.perform(delete("/pages/1"));
        verify(repository).deleteById(1L);
    }
}