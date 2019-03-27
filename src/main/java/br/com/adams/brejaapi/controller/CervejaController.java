package br.com.adams.brejaapi.controller;

import br.com.adams.brejaapi.dto.EstiloTemperaturaDto;
import br.com.adams.brejaapi.model.Cerveja;
import br.com.adams.brejaapi.model.Endereco;
import br.com.adams.brejaapi.rest.CepClient;
import br.com.adams.brejaapi.service.CervejaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cerveja")
public class CervejaController {

  private final CervejaService service;
  private final CepClient cepClient;

  @GetMapping
  public List<Cerveja> listar() {
    return service.listar();
  }

  @GetMapping(value = "/{id}")
  public Cerveja buscarPorId(@NotNull @PathVariable final Long id) {
    return service.buscarPorId(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Cerveja salvar(@Valid @RequestBody final Cerveja cerveja) {
    return service.criarCerveja(cerveja);
  }

  @PutMapping(value = "/{id}")
  public Cerveja editar(@PathVariable final Long id, @Valid @RequestBody final Cerveja cerveja) {
    return service.editar(id, cerveja);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void excluir(@PathVariable final Long id) {
    service.excluir(id);
  }

  @PutMapping(value = "/playlist")
  public List<Cerveja> getPlaylist(@Valid @RequestBody final EstiloTemperaturaDto temperaturaDto) {
    return service.buscarPlaylist(temperaturaDto);
  }

  //   @TODO REMOVER
  @GetMapping(value = "/cep-search/{cep}")
  public Endereco getCep(@PathVariable final String cep) {
    return cepClient.buscaEnderecoPor(cep);
  }
}
