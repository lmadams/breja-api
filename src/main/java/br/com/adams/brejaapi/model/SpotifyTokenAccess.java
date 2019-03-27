package br.com.adams.brejaapi.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SpotifyTokenAccess {

  private String access_token;
  private String token_type;
  private Long expires_in;
  private String scope;
}
