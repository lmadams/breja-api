package br.com.adams.brejaapi.controller;

import br.com.adams.brejaapi.BrejaApiApplication;
import br.com.adams.brejaapi.model.Cerveja;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BrejaApiApplication.class)
@WebAppConfiguration
public class CervejaControllerTest {

  @Autowired private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void listar() throws Exception {
    mockMvc
        .perform(get("/cerveja"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[0].nome", is("Weissbier")))
        .andExpect(jsonPath("$[0].tempInicial", is(-1)))
        .andExpect(jsonPath("$[0].tempFinal", is(3)));
  }

  @Test
  public void buscarPorId() throws Exception {
    mockMvc
        .perform(get("/cerveja/5"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(5)))
        .andExpect(jsonPath("$.nome", is("India pale ale")))
        .andExpect(jsonPath("$.tempInicial", is(-6)))
        .andExpect(jsonPath("$.tempFinal", is(7)));
  }

  @Test
  public void salvar() throws Exception {
    final Cerveja cerveja =
        Cerveja.builder().nome("Cerveja Nova").tempInicial(-1L).tempFinal(2L).build();
    mockMvc
        .perform(
            post("/cerveja").contentType(MediaType.APPLICATION_JSON).content(asJsonString(cerveja)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(10)))
        .andExpect(jsonPath("$.nome", is(cerveja.getNome())))
        .andExpect(jsonPath("$.tempInicial", is(cerveja.getTempInicial().intValue())))
        .andExpect(jsonPath("$.tempFinal", is(cerveja.getTempFinal().intValue())));
  }

  @Test
  public void editar() throws Exception {
    final Cerveja cerveja =
        Cerveja.builder().nome("Cerveja Editada").tempInicial(-1L).tempFinal(2L).build();

    mockMvc
        .perform(
            put("/cerveja/6")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cerveja)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(6)))
        .andExpect(jsonPath("$.nome", is(cerveja.getNome())))
        .andExpect(jsonPath("$.tempInicial", is(cerveja.getTempInicial().intValue())))
        .andExpect(jsonPath("$.tempFinal", is(cerveja.getTempFinal().intValue())));
  }

  @Test
  public void excluir() throws Exception {
    mockMvc.perform(delete("/cerveja/9")).andExpect(status().isNoContent());
  }

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
