package com.sqli.workshop.ddd.connaissance.client.db;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConnaissanceClientDbRepository extends MongoRepository<ConnaissanceClientDb, UUID> {
}
