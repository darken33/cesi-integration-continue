package com.sqli.workshop.ddd.connaissance.client.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Adresse {

  @NotNull
  private String ligne1;

  private Optional<String> ligne2 = Optional.empty();

  @NotNull
  private String codePostal;

  @NotNull
  private String ville;

}
