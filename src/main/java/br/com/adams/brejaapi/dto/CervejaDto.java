package br.com.adams.brejaapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CervejaDto {

  private Long id;
  private String nome;
  private Long tempInicial;
  private Long tempFinal;
  private List<TrackDto> tracks;
}
