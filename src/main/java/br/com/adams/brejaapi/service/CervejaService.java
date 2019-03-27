package br.com.adams.brejaapi.service;

import br.com.adams.brejaapi.dto.CervejaDto;
import br.com.adams.brejaapi.dto.EstiloTemperaturaDto;
import br.com.adams.brejaapi.model.Cerveja;

import java.util.List;

public interface CervejaService {
  List<Cerveja> listar();

  Cerveja buscarPorId(Long id);

  Cerveja criarCerveja(Cerveja cerveja);

  Cerveja editar(Long id, Cerveja cerveja);

  void excluir(Long id);

  List<CervejaDto> buscarPlaylist(EstiloTemperaturaDto temperaturaDto);
}
