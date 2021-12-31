package com.sqli.workshop.ddd.connaissance.client.generated.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ConnaissanceClientDto
 */

public class ConnaissanceClientDto   {
  @JsonProperty("id")
  private String id;

  @JsonProperty("nom")
  private String nom;

  @JsonProperty("prenom")
  private String prenom;

  @JsonProperty("ligne1")
  private String ligne1;

  @JsonProperty("ligne2")
  private String ligne2;

  @JsonProperty("codePostal")
  private String codePostal;

  @JsonProperty("ville")
  private String ville;

  @JsonProperty("situationFamilialle")
  private String situationFamilialle;

  @JsonProperty("nombreEnfants")
  private Integer nombreEnfants;

  public ConnaissanceClientDto id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  @ApiModelProperty(value = "")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ConnaissanceClientDto nom(String nom) {
    this.nom = nom;
    return this;
  }

  /**
   * Get nom
   * @return nom
  */
  @ApiModelProperty(value = "")


  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public ConnaissanceClientDto prenom(String prenom) {
    this.prenom = prenom;
    return this;
  }

  /**
   * Get prenom
   * @return prenom
  */
  @ApiModelProperty(value = "")


  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public ConnaissanceClientDto ligne1(String ligne1) {
    this.ligne1 = ligne1;
    return this;
  }

  /**
   * Get ligne1
   * @return ligne1
  */
  @ApiModelProperty(value = "")


  public String getLigne1() {
    return ligne1;
  }

  public void setLigne1(String ligne1) {
    this.ligne1 = ligne1;
  }

  public ConnaissanceClientDto ligne2(String ligne2) {
    this.ligne2 = ligne2;
    return this;
  }

  /**
   * Get ligne2
   * @return ligne2
  */
  @ApiModelProperty(value = "")


  public String getLigne2() {
    return ligne2;
  }

  public void setLigne2(String ligne2) {
    this.ligne2 = ligne2;
  }

  public ConnaissanceClientDto codePostal(String codePostal) {
    this.codePostal = codePostal;
    return this;
  }

  /**
   * Get codePostal
   * @return codePostal
  */
  @ApiModelProperty(value = "")


  public String getCodePostal() {
    return codePostal;
  }

  public void setCodePostal(String codePostal) {
    this.codePostal = codePostal;
  }

  public ConnaissanceClientDto ville(String ville) {
    this.ville = ville;
    return this;
  }

  /**
   * Get ville
   * @return ville
  */
  @ApiModelProperty(value = "")


  public String getVille() {
    return ville;
  }

  public void setVille(String ville) {
    this.ville = ville;
  }

  public ConnaissanceClientDto situationFamilialle(String situationFamilialle) {
    this.situationFamilialle = situationFamilialle;
    return this;
  }

  /**
   * Get situationFamilialle
   * @return situationFamilialle
  */
  @ApiModelProperty(value = "")


  public String getSituationFamilialle() {
    return situationFamilialle;
  }

  public void setSituationFamilialle(String situationFamilialle) {
    this.situationFamilialle = situationFamilialle;
  }

  public ConnaissanceClientDto nombreEnfants(Integer nombreEnfants) {
    this.nombreEnfants = nombreEnfants;
    return this;
  }

  /**
   * Get nombreEnfants
   * @return nombreEnfants
  */
  @ApiModelProperty(value = "")


  public Integer getNombreEnfants() {
    return nombreEnfants;
  }

  public void setNombreEnfants(Integer nombreEnfants) {
    this.nombreEnfants = nombreEnfants;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConnaissanceClientDto connaissanceClient = (ConnaissanceClientDto) o;
    return Objects.equals(this.id, connaissanceClient.id) &&
        Objects.equals(this.nom, connaissanceClient.nom) &&
        Objects.equals(this.prenom, connaissanceClient.prenom) &&
        Objects.equals(this.ligne1, connaissanceClient.ligne1) &&
        Objects.equals(this.ligne2, connaissanceClient.ligne2) &&
        Objects.equals(this.codePostal, connaissanceClient.codePostal) &&
        Objects.equals(this.ville, connaissanceClient.ville) &&
        Objects.equals(this.situationFamilialle, connaissanceClient.situationFamilialle) &&
        Objects.equals(this.nombreEnfants, connaissanceClient.nombreEnfants);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nom, prenom, ligne1, ligne2, codePostal, ville, situationFamilialle, nombreEnfants);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConnaissanceClientDto {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    nom: ").append(toIndentedString(nom)).append("\n");
    sb.append("    prenom: ").append(toIndentedString(prenom)).append("\n");
    sb.append("    ligne1: ").append(toIndentedString(ligne1)).append("\n");
    sb.append("    ligne2: ").append(toIndentedString(ligne2)).append("\n");
    sb.append("    codePostal: ").append(toIndentedString(codePostal)).append("\n");
    sb.append("    ville: ").append(toIndentedString(ville)).append("\n");
    sb.append("    situationFamilialle: ").append(toIndentedString(situationFamilialle)).append("\n");
    sb.append("    nombreEnfants: ").append(toIndentedString(nombreEnfants)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

