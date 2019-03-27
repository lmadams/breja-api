package br.com.adams.brejaapi.rest;

import br.com.adams.brejaapi.model.Endereco;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "https://viacep.com.br/ws/", name = "viacep")
public interface CepClient {

  @GetMapping("{cep}/json")
  Endereco buscaEnderecoPor(@PathVariable("cep") String cep);
}
