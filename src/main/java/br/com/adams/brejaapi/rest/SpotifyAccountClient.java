package br.com.adams.brejaapi.rest;

import br.com.adams.brejaapi.config.FeignClientConfiguration;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(
    name = "spotifyaccountclient",
    url = "https://accounts.spotify.com",
    configuration = FeignClientConfiguration.class)
public interface SpotifyAccountClient {

  @PostMapping(value = "/api/token")
  @Headers("Content-Type: application/x-www-form-urlencoded")
  void getToken(@RequestBody Map<String, String> formParams);
}
