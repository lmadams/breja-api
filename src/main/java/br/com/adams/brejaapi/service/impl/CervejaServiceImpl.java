package br.com.adams.brejaapi.service.impl;

import br.com.adams.brejaapi.model.Cerveja;
import br.com.adams.brejaapi.repository.CervejaRepository;
import br.com.adams.brejaapi.service.CervejaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CervejaServiceImpl implements CervejaService {

  private final CervejaRepository repository;

  @Override
  public List<Cerveja> listar() {
    return repository.findAll();
  }

  @Override
  public Cerveja buscarPorId(final Long id) {
    return repository
        .findById(id)
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    String.format("Cerveja com o id: %s não encontrada!", id)));
  }

  @Override
  public Cerveja criarCerveja(final Cerveja cerveja) {
    return repository.save(cerveja);
  }

  @Override
  public Cerveja editar(final Long id, final Cerveja cerveja) {
    final Cerveja cervejaEdit = buscarPorId(id);
    cervejaEdit.setNome(cerveja.getNome());
    cervejaEdit.setTempInicial(cerveja.getTempInicial());
    cervejaEdit.setTempFinal(cerveja.getTempFinal());
    return repository.save(cervejaEdit);
  }

  @Override
  public void excluir(final Long id) {
    final Cerveja cervejaRemove = buscarPorId(id);
    repository.delete(cervejaRemove);
  }
}
