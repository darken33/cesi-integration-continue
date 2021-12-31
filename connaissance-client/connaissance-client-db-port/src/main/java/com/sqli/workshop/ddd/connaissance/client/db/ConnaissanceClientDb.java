package com.sqli.workshop.ddd.connaissance.client.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * Objet Connaissance Client pour la base de données
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "connaissanceclient")
public class ConnaissanceClientDb {

    @Id
    private UUID id;

    private String nom;

    private String prenom;

    private String ligne1;

    private String ligne2;

    private String codePostal;

    private String ville;

    private String situationFamiliale;

    private Integer nombreEnfants;

}
