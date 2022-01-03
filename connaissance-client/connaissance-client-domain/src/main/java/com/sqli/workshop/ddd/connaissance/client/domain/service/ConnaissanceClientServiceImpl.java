package com.sqli.workshop.ddd.connaissance.client.domain.service;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.sqli.workshop.ddd.connaissance.client.domain.Adresse;
import com.sqli.workshop.ddd.connaissance.client.domain.enums.SituationFamiliale;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sqli.workshop.ddd.connaissance.client.domain.ConnaissanceClient;
import com.sqli.workshop.ddd.connaissance.client.domain.interfaces.ConnaissanceClientRepository;
import com.sqli.workshop.ddd.connaissance.client.domain.interfaces.ConnaissanceClientService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ConnaissanceClientServiceImpl implements ConnaissanceClientService {

    private static final Logger LOG = LoggerFactory.getLogger(ConnaissanceClientServiceImpl.class);

    private final ConnaissanceClientRepository repository;

    public List<ConnaissanceClient> listerClients() {
        return repository.findAll();
    }

    @Async
    public ConnaissanceClient nouveauClientAsync(ConnaissanceClient connaissanceClient) {
        return nouveauClient(connaissanceClient);
    }

    public ConnaissanceClient nouveauClient(ConnaissanceClient connaissanceClient) {
        connaissanceClient.setNom(connaissanceClient.getNom().toUpperCase());
        ConnaissanceClient result = repository.save(connaissanceClient);
        return result;
    }

    public Optional<ConnaissanceClient> informationsClient(@NotNull String id) {
        return repository.findById(id);
    }

    public Optional<ConnaissanceClient> changementAdresse(@NotNull String id, Adresse adresse, Boolean sendEvent) {
        ConnaissanceClient result = null;
        Optional<ConnaissanceClient> connaissanceClient = repository.findById(id);
        if (connaissanceClient.isPresent()) {
            connaissanceClient.get().setAdresse(adresse);
            result = repository.save(connaissanceClient.get());
        }
        return Optional.ofNullable(result);
    }

    public Optional<ConnaissanceClient> changementSituation(@NotNull String id, SituationFamiliale situationFamiliale, Integer nombreEnfants) {
        ConnaissanceClient result = null;
        Optional<ConnaissanceClient> connaissanceClient = repository.findById(id);
        if (connaissanceClient.isPresent()) {
            connaissanceClient.get().setSituationFamiliale(situationFamiliale);
            connaissanceClient.get().setNombreEnfants(nombreEnfants);
            result = repository.save(connaissanceClient.get());
        }
        return Optional.ofNullable(result);
    }

}
