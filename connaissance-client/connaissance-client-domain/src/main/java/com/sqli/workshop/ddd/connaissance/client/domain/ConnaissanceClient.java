package com.sqli.workshop.ddd.connaissance.client.domain;

import java.io.Serializable;

import com.sqli.workshop.ddd.connaissance.client.domain.enums.SituationFamiliale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * ConnaissanceClient
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnaissanceClient implements Serializable {

    private String id;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    @NotNull
    private Adresse adresse;

    @NotNull
    private SituationFamiliale situationFamiliale;

    @NotNull
    private Integer nombreEnfants;

}

