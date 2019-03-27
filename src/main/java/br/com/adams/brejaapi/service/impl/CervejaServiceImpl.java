package br.com.adams.brejaapi.service.impl;

import br.com.adams.brejaapi.dto.CervejaDto;
import br.com.adams.brejaapi.dto.EstiloTemperaturaDto;
import br.com.adams.brejaapi.dto.TrackDto;
import br.com.adams.brejaapi.model.Cerveja;
import br.com.adams.brejaapi.repository.CervejaRepository;
import br.com.adams.brejaapi.service.CervejaService;
import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CervejaServiceImpl implements CervejaService {
  private static final String CLIENT_ID = "013c2d83b0764d8d986d8228ea0a4432";
  private static final String CLIENT_SECRET = "d9b15670fa8e40159af404af47ce1841";
  private static final SpotifyApi spotifyApi =
      new SpotifyApi.Builder().setClientId(CLIENT_ID).setClientSecret(CLIENT_SECRET).build();
  private static final ClientCredentialsRequest clientCredentialsRequest =
      spotifyApi.clientCredentials().build();

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

  @Override
  public List<CervejaDto> buscarPlaylist(EstiloTemperaturaDto temperaturaDto) {
    final List<Cerveja> cervejas = getCervejasPorTemp(listar(), temperaturaDto.getTemperatura());
    List<CervejaDto> cervejaComPlayList = new ArrayList<>();
    try {
      spotifyApi.setAccessToken(clientCredentialsRequest.execute().getAccessToken());
      cervejaComPlayList =
          cervejas.stream()
              .map(
                  cerveja -> {
                    Paging<Track> spotifyExecute;
                    List<TrackDto> tracks = new ArrayList<>();
                    try {
                      spotifyExecute =
                          spotifyApi
                              .searchTracks(cerveja.getNome())
                              .market(CountryCode.BR)
                              .limit(10)
                              .offset(0)
                              .build()
                              .execute();

                      tracks =
                          Arrays.stream(spotifyExecute.getItems())
                              .map(
                                  track ->
                                      TrackDto.builder()
                                          .nome(track.getName())
                                          .artista(track.getArtists()[0].getName())
                                          .link(track.getPreviewUrl())
                                          .build())
                              .collect(Collectors.toList());
                    } catch (IOException | SpotifyWebApiException e) {
                      System.out.println("Error: " + e.getMessage());
                    }

                    return CervejaDto.builder()
                        .id(cerveja.getId())
                        .nome(cerveja.getNome())
                        .tempFinal(cerveja.getTempFinal())
                        .tempInicial(cerveja.getTempInicial())
                        .tracks(tracks)
                        .build();
                  })
              .collect(Collectors.toList());
    } catch (IOException | SpotifyWebApiException e) {
      System.out.println("Error: " + e.getMessage());
    }
    return cervejaComPlayList;
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
