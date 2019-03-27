package br.com.adams.brejaapi.rest;

import br.com.adams.brejaapi.model.Track;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "https://api.spotify.com/", name = "spotifyclient")
public interface SpotifyClient {

  @GetMapping("{search}")
  Track buscaPlaylist(@PathVariable("search") String search);
}
