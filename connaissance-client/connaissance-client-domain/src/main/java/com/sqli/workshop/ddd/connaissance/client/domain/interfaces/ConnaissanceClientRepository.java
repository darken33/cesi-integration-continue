package com.sqli.workshop.ddd.connaissance.client.domain.interfaces;

import java.util.List;
import java.util.Optional;

import com.sqli.workshop.ddd.connaissance.client.domain.ConnaissanceClient;

public interface ConnaissanceClientRepository {

    ConnaissanceClient save(ConnaissanceClient connaissanceClient);

    Optional<ConnaissanceClient> findById(String id);

    List<ConnaissanceClient> findAll();

}
