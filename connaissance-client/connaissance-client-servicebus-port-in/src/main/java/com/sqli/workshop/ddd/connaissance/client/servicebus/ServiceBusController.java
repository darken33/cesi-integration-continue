package com.sqli.workshop.ddd.connaissance.client.servicebus;

import com.sqli.workshop.ddd.MessageFiscalite;
import com.sqli.workshop.ddd.connaissance.client.domain.Adresse;
import com.sqli.workshop.ddd.connaissance.client.domain.interfaces.ConnaissanceClientService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class ServiceBusController {

  private static final String QUEUE_NAME = "queue2";

  private final Logger logger = LoggerFactory.getLogger(ServiceBusController.class);

  private ConnaissanceClientService service;

  @JmsListener(destination = QUEUE_NAME, containerFactory = "jmsListenerContainerFactory")
  public void receiveMessage(MessageFiscalite message) {
    service.changementAdresse(message.getId(), mapToDomain(message), Boolean.FALSE) ;
  }

  private Adresse mapToDomain(MessageFiscalite message) {
    return new Adresse(
        message.getLigne1(),
        Optional.ofNullable(message.getLigne2()),
        message.getCodePostal(),
        message.getVille()
    );
  }

}
