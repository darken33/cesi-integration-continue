package com.sqli.workshop.ddd.connaissance.client.generated.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SituationDto
 */

public class SituationDto   {
  @JsonProperty("situationFamilialle")
  private String situationFamilialle;

  @JsonProperty("nombreEnfants")
  private Integer nombreEnfants;

  public SituationDto situationFamilialle(String situationFamilialle) {
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

  public SituationDto nombreEnfants(Integer nombreEnfants) {
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
    SituationDto situation = (SituationDto) o;
    return Objects.equals(this.situationFamilialle, situation.situationFamilialle) &&
        Objects.equals(this.nombreEnfants, situation.nombreEnfants);
  }

  @Override
  public int hashCode() {
    return Objects.hash(situationFamilialle, nombreEnfants);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SituationDto {\n");
    
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

