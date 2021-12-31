package com.sqli.workshop.ddd.connaissance.client.db;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.sqli.workshop.ddd.connaissance.client.domain.Adresse;
import com.sqli.workshop.ddd.connaissance.client.domain.ConnaissanceClient;
import com.sqli.workshop.ddd.connaissance.client.domain.enums.SituationFamiliale;
import com.sqli.workshop.ddd.connaissance.client.domain.interfaces.ConnaissanceClientRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConnaissanceClientRepositoryImpl implements ConnaissanceClientRepository {

    private static final Logger LOG = LoggerFactory.getLogger(ConnaissanceClientRepositoryImpl.class);

    private final ConnaissanceClientDbRepository dbRepository;

    @Override
    public ConnaissanceClient save(ConnaissanceClient connaissanceClient) {
        ConnaissanceClientDb connaissanceClientDb = mapToDb(connaissanceClient);
        connaissanceClientDb = dbRepository.save(connaissanceClientDb);
        return mapToDomain(connaissanceClientDb);
    }


    private ConnaissanceClientDb mapToDb(ConnaissanceClient connaissanceClient) {
        ConnaissanceClientDb ccDb = new ConnaissanceClientDb();
        ccDb.setId(connaissanceClient.getId() != null ? UUID.fromString(connaissanceClient.getId()) : UUID.randomUUID());
        ccDb.setLigne1(connaissanceClient.getAdresse().getLigne1());
        ccDb.setLigne2(connaissanceClient.getAdresse().getLigne2().orElse(null));
        ccDb.setCodePostal(connaissanceClient.getAdresse().getCodePostal());
        ccDb.setVille(connaissanceClient.getAdresse().getVille());
        ccDb.setNom(connaissanceClient.getNom());
        ccDb.setPrenom(connaissanceClient.getPrenom());
        ccDb.setSituationFamiliale(connaissanceClient.getSituationFamiliale().toString());
        ccDb.setNombreEnfants(connaissanceClient.getNombreEnfants());
        return ccDb;
    }

    private ConnaissanceClient mapToDomain(ConnaissanceClientDb connaissanceClientDb) {
        ConnaissanceClient cc = new ConnaissanceClient();
        cc.setId(connaissanceClientDb.getId().toString());
        cc.setNom(connaissanceClientDb.getNom());
        cc.setPrenom(connaissanceClientDb.getPrenom());
        cc.setAdresse(new Adresse(connaissanceClientDb.getLigne1(),
            Optional.ofNullable(connaissanceClientDb.getLigne2()),
            connaissanceClientDb.getCodePostal(),
            connaissanceClientDb.getVille()
            )
        );
        cc.setSituationFamiliale(SituationFamiliale.fromValue(connaissanceClientDb.getSituationFamiliale()));
        cc.setNombreEnfants(connaissanceClientDb.getNombreEnfants());
        return cc;
    }

    @Override
    public Optional<ConnaissanceClient> findById(String id) {
        Optional<ConnaissanceClientDb> connaissanceClientDb = dbRepository.findById(UUID.fromString(id));
        return connaissanceClientDb.map(this::mapToDomain);
    }

    @Override
    public List<ConnaissanceClient> findAll() {
        List<ConnaissanceClientDb> connaissanceClientDbs = dbRepository.findAll();
        return connaissanceClientDbs.stream().map(this::mapToDomain).collect(Collectors.toList());
    }
}
