package br.com.adams.brejaapi.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Endereco {

  private String cep;
  private String logradouro;
  private String complemento;
  private String bairro;
  private String localidade;
  private String uf;
}
