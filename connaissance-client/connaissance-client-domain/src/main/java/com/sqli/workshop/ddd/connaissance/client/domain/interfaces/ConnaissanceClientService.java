package com.sqli.workshop.ddd.connaissance.client.domain.interfaces;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.sqli.workshop.ddd.connaissance.client.domain.Adresse;
import com.sqli.workshop.ddd.connaissance.client.domain.ConnaissanceClient;
import com.sqli.workshop.ddd.connaissance.client.domain.enums.SituationFamiliale;

public interface ConnaissanceClientService {

    ConnaissanceClient nouveauClient(ConnaissanceClient connaissanceClient);

    ConnaissanceClient nouveauClientAsync(ConnaissanceClient connaissanceClient);

    List<ConnaissanceClient> listerClients();

    Optional<ConnaissanceClient> informationsClient(@NotNull String id);

    Optional<ConnaissanceClient> changementAdresse(@NotNull String id, Adresse adresse, Boolean sendEvent);

    Optional<ConnaissanceClient> changementSituation(@NotNull String id, SituationFamiliale situationFamiliale, Integer nombreEnfants);
}
