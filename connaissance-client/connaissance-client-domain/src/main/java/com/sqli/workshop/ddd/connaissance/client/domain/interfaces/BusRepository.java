package com.sqli.workshop.ddd.connaissance.client.domain.interfaces;

import com.sqli.workshop.ddd.connaissance.client.domain.ConnaissanceClient;

public interface BusRepository {

  void sendEvent(ConnaissanceClient connaissanceClient);

}
