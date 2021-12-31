package com.sqli.workshop.ddd.connaissance.client;

import com.sqli.workshop.ddd.connaissance.client.db.ConnaissanceClientDb;
import com.sqli.workshop.ddd.connaissance.client.db.ConnaissanceClientDbRepository;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
@EnableMongoRepositories
@EnableAsync
@ComponentScan({"com.sqli.workshop.ddd.connaissance.client.*"})
public class ConnaissanceClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConnaissanceClientApplication.class, args);
    }

}
