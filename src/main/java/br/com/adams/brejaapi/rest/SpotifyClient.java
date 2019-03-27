package br.com.adams.brejaapi.rest;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url = "https://api.spotify.com/", name = "spotifyclient")
public interface SpotifyClient {}
