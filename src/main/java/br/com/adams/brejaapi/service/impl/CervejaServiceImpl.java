package br.com.adams.brejaapi.service.impl;

import br.com.adams.brejaapi.dto.EstiloTemperaturaDto;
import br.com.adams.brejaapi.model.Cerveja;
import br.com.adams.brejaapi.repository.CervejaRepository;
import br.com.adams.brejaapi.rest.SpotifyAccountClient;
import br.com.adams.brejaapi.service.CervejaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CervejaServiceImpl implements CervejaService {

  private final CervejaRepository repository;
  private final SpotifyAccountClient accountClient;

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

  @Override
  public List<Cerveja> buscarPlaylist(EstiloTemperaturaDto temperaturaDto) {
    return getCervejasPorTemp(listar(), temperaturaDto.getTemperatura());

    //    HashMap<String, String> params = new HashMap<>();
    //    params.put("grant_type", "client_credentials");
    //
    //    accountClient.getToken(params);
    //
    //    System.out.println(credentials);

    //    cervejas.stream().map(cerveja -> spotifyClient.buscaPlaylist(cerveja.getNome()));

    //    return cervejas;
  }

  private List<Cerveja> getCervejasPorTemp(final List<Cerveja> lista, final double temperatura) {
    final List<Cerveja> cervejasOrdenadas =
        lista.stream()
            .sorted((Comparator.comparingDouble(Cerveja::getTempMedia)))
            .collect(Collectors.toList());

    List<Cerveja> cervejas =
        cervejasOrdenadas.stream()
            .filter(cerveja -> cerveja.getTempMedia() == temperatura)
            .sorted((Comparator.comparing(Cerveja::getNome)))
            .collect(Collectors.toList());

    if (cervejas.isEmpty()) {
      Optional<Double> menorDiferenca =
          cervejasOrdenadas.stream()
              .map(cerveja -> temperatura - cerveja.getTempMedia())
              .map(
                  aDouble -> {
                    if (aDouble < 0) {
                      return aDouble * -1;
                    }
                    return aDouble;
                  })
              .min(Double::compareTo);

      return cervejasOrdenadas.stream()
          .filter(
              cerveja -> {
                if (cerveja.getTempMedia() == temperatura + menorDiferenca.get()) {
                  return true;
                }
                return cerveja.getTempMedia() == temperatura - menorDiferenca.get();
              })
          .sorted((Comparator.comparing(Cerveja::getNome)))
          .collect(Collectors.toList());
    }

    return cervejas;
  }
}
