package com.sqli.workshop.ddd.connaissance.client.api;

import com.sqli.workshop.ddd.connaissance.client.domain.Adresse;
import com.sqli.workshop.ddd.connaissance.client.domain.ConnaissanceClient;
import com.sqli.workshop.ddd.connaissance.client.domain.enums.SituationFamiliale;
import com.sqli.workshop.ddd.connaissance.client.domain.interfaces.ConnaissanceClientService;
import com.sqli.workshop.ddd.connaissance.client.generated.api.model.AdresseDto;
import com.sqli.workshop.ddd.connaissance.client.generated.api.model.ApiErrorResponseDto;
import com.sqli.workshop.ddd.connaissance.client.generated.api.model.ConnaissanceClientDto;
import com.sqli.workshop.ddd.connaissance.client.generated.api.model.SituationDto;
import com.sqli.workshop.ddd.connaissance.client.generated.api.server.ConnaissanceClientASApiDelegate;
import com.sqli.workshop.ddd.connaissance.client.generated.api.server.ConnaissanceClientApiDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ConnaissanceClientDelegate implements ConnaissanceClientApiDelegate, ConnaissanceClientASApiDelegate {

  private ConnaissanceClientService service;

  @Autowired
  public ConnaissanceClientDelegate(ConnaissanceClientService service){
    this.service = service;
  }

  @Override
  public ResponseEntity<List<ConnaissanceClientDto>> getConnaissanceClients() {
    return ResponseEntity.ok(
        service.listerClients().stream()
            .map(this::mapToDto).collect(Collectors.toList()));
  }

  @Override
  public ResponseEntity<ConnaissanceClientDto> getConnaissanceClient(String id) {
    Optional<ConnaissanceClient> connaissanceClient = service.informationsClient(id);
    if (connaissanceClient.isPresent()) {
      return ResponseEntity.ok(mapToDto(connaissanceClient.get()));
    }
    else {
      return (ResponseEntity<ConnaissanceClientDto>) ResponseEntity.notFound();
    }
  }

  @Override
  public ResponseEntity<ConnaissanceClientDto> saveConnaissanceClient(ConnaissanceClientDto connaissanceClientDto) {
    ConnaissanceClient connaissanceClient = service.nouveauClient(mapToDomain(connaissanceClientDto));
    return ResponseEntity.ok(mapToDto(connaissanceClient));
  }

  @Override
  public ResponseEntity<ApiErrorResponseDto> saveConnaissanceClientAsync(ConnaissanceClientDto connaissanceClientDto) {
    ConnaissanceClient connaissanceClient = service.nouveauClientAsync(mapToDomain(connaissanceClientDto));
    ApiErrorResponseDto retour = new ApiErrorResponseDto();
    retour.status(202);
    return ResponseEntity.accepted().body(retour);
  }

  @Override
  public ResponseEntity<ConnaissanceClientDto> changerSituation(String id, SituationDto situationDto) {
    Optional<ConnaissanceClient>  connaissanceClient = service.changementSituation(id, SituationFamiliale.fromValue(situationDto.getSituationFamilialle()), situationDto.getNombreEnfants());
    if (connaissanceClient.isPresent()) {
      return ResponseEntity.ok(mapToDto(connaissanceClient.get()));
    }
    else {
      return (ResponseEntity<ConnaissanceClientDto>) ResponseEntity.notFound();
    }
  }

  @Override
  public Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  @Override
  public ResponseEntity<ConnaissanceClientDto> changerAdresse(String id, AdresseDto adresseDto) {
    Optional<ConnaissanceClient>  connaissanceClient = service.changementAdresse(id, mapToAdresseDomain(adresseDto), Boolean.TRUE);
    if (connaissanceClient.isPresent()) {
      return ResponseEntity.ok(mapToDto(connaissanceClient.get()));
    }
    else {
      return (ResponseEntity<ConnaissanceClientDto>) ResponseEntity.notFound();
    }
  }

  private ConnaissanceClientDto mapToDto(ConnaissanceClient connaissanceClient) {
    ConnaissanceClientDto connaissanceClientDto = new ConnaissanceClientDto();
    connaissanceClientDto.setId(connaissanceClient.getId());
    connaissanceClientDto.setNom(connaissanceClient.getNom());
    connaissanceClientDto.setPrenom(connaissanceClient.getPrenom());
    connaissanceClientDto.setLigne1(connaissanceClient.getAdresse().getLigne1());
    connaissanceClientDto.setLigne2(connaissanceClient.getAdresse().getLigne2().orElse(null));
    connaissanceClientDto.setCodePostal(connaissanceClient.getAdresse().getCodePostal());
    connaissanceClientDto.setVille(connaissanceClient.getAdresse().getVille());
    connaissanceClientDto.setSituationFamilialle(connaissanceClient.getSituationFamiliale().toString());
    connaissanceClientDto.setNombreEnfants(connaissanceClient.getNombreEnfants());
    return connaissanceClientDto;
  }

  private ConnaissanceClient mapToDomain(ConnaissanceClientDto connaissanceClientDto) {
    ConnaissanceClient connaissanceClient = new ConnaissanceClient();
    if (connaissanceClientDto.getId() != null) {
      connaissanceClient.setId(connaissanceClientDto.getId());
    }
    connaissanceClient.setNom(connaissanceClientDto.getNom());
    connaissanceClient.setPrenom(connaissanceClientDto.getPrenom());
    Adresse adresse = new Adresse();
    adresse.setLigne1(connaissanceClientDto.getLigne1());
    adresse.setLigne2(Optional.ofNullable(connaissanceClientDto.getLigne2()));
    adresse.setCodePostal(connaissanceClientDto.getCodePostal());
    adresse.setVille(connaissanceClientDto.getVille());
    connaissanceClient.setAdresse(adresse);
    connaissanceClient.setSituationFamiliale(SituationFamiliale.fromValue(connaissanceClientDto.getSituationFamilialle()));
    connaissanceClient.setNombreEnfants(connaissanceClientDto.getNombreEnfants());
    return connaissanceClient;
  }
  private Adresse mapToAdresseDomain(AdresseDto adressDto) {
    Adresse adresse = new Adresse();
    adresse.setLigne1(adressDto.getLigne1());
    adresse.setLigne2(Optional.ofNullable(adressDto.getLigne2()));
    adresse.setCodePostal(adressDto.getCodePostal());
    adresse.setVille(adressDto.getVille());
    return adresse;
  }
}
