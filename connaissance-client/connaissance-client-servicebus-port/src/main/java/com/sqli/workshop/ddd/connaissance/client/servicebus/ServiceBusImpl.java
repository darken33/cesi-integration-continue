package com.sqli.workshop.ddd.connaissance.client.servicebus;

import com.sqli.workshop.ddd.Message;
import com.sqli.workshop.ddd.connaissance.client.domain.ConnaissanceClient;
import com.sqli.workshop.ddd.connaissance.client.domain.interfaces.BusRepository;
import lombok.AllArgsConstructor;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@EnableJms
@AllArgsConstructor
public class ServiceBusImpl implements BusRepository {

  private static final String QUEUE_NAME = "queue1";

  private JmsTemplate jmsTemplate;

  @Override
  public void sendEvent(ConnaissanceClient connaissanceClient) {
    Message message = map(connaissanceClient);
    jmsTemplate.convertAndSend(QUEUE_NAME, message);
  }

  private Message map(ConnaissanceClient connaissanceClient) {
    Message message = new Message();
    message.setId(connaissanceClient.getId());
    message.setCodePostal(connaissanceClient.getAdresse().getCodePostal());
    message.setLigne1(connaissanceClient.getAdresse().getLigne1());
    message.setLigne2(connaissanceClient.getAdresse().getLigne2().orElse(""));
    message.setVille(connaissanceClient.getAdresse().getVille());
    message.setNom(connaissanceClient.getNom());
    message.setPrenom(connaissanceClient.getPrenom());
    message.setSituationFamilialle(connaissanceClient.getSituationFamiliale().name());
    message.setNombreEnfants(connaissanceClient.getNombreEnfants());
    return message;
  }
}
