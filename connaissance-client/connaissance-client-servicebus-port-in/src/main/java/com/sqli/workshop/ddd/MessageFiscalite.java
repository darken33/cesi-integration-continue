package com.sqli.workshop.ddd;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageFiscalite implements Serializable {

  private String id;

  private String ligne1;

  private String ligne2;

  private String codePostal;

  private String ville;

}

