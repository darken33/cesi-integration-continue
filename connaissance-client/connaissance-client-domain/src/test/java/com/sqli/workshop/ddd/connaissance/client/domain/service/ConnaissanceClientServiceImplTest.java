package com.sqli.workshop.ddd.connaissance.client.domain.service;


import com.sqli.workshop.ddd.connaissance.client.domain.Adresse;
import com.sqli.workshop.ddd.connaissance.client.domain.ConnaissanceClient;
import com.sqli.workshop.ddd.connaissance.client.domain.enums.SituationFamiliale;
import com.sqli.workshop.ddd.connaissance.client.domain.interfaces.ConnaissanceClientRepository;
import com.sqli.workshop.ddd.connaissance.client.domain.interfaces.ConnaissanceClientService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;

import java.util.*;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.*;

//@RunWith(SpringRunner.class)
public class ConnaissanceClientServiceImplTest {

 private ConnaissanceClientService service;

 private ConnaissanceClientRepository repository;

  private static Object answer(InvocationOnMock invocationOnMock) {
    return invocationOnMock.getArgument(0);
  }

  @Before
 public void init() {
   this.repository = mock(ConnaissanceClientRepository.class);
   this.service = new ConnaissanceClientServiceImpl(repository);
 }

 @Test
 public void given_connaissance_client_not_null_get_should_return_data() {
   // GIVEN
   ConnaissanceClient cc = new ConnaissanceClient("1", "Bousquet", "Philippe", new Adresse("48 rue bauducheu", null, "33800", "Bordeaux"), SituationFamiliale.CELIBATAIRE, 0);
   when(repository.findById("1")).thenReturn(Optional.of(cc));
   // WHEN
   Optional<ConnaissanceClient> result = service.informationsClient("1");
   // THEN
   assertTrue(result.isPresent());
   assertEquals("1", result.get().getId());
   assertEquals("Bousquet", result.get().getNom());
   verify(repository).findById("1");
 }


  @Test
  public void given_connaissance_client_null_get_should_return_empty() {
    // GIVEN
    when(repository.findById("1")).thenReturn(Optional.empty());
    // WHEN
    Optional<ConnaissanceClient> result = service.informationsClient("1");
    // THEN
    assertTrue(result.isEmpty());
    verify(repository).findById("1");
  }


  @Test
  public void given_connaissance_client_save_return_id() {
    // GIVEN
    ConnaissanceClient ccToSave = new ConnaissanceClient(null, "Bousquet", "Philippe", new Adresse("48 rue bauducheu", Optional.empty(), "33800", "Bordeaux"), SituationFamiliale.CELIBATAIRE, 0);
//    ConnaissanceClient ccSaved = new ConnaissanceClient("1", "BOUSQUET", "Philippe", new Adresse("48 rue bauducheu", Optional.empty(), "33800", "Bordeaux"), SituationFamiliale.CELIBATAIRE, 0);
//    when(repository.save(any())).thenReturn(ccSaved);
    when(repository.save(any())).thenAnswer(invocationOnMock -> {
      ConnaissanceClient _cc = invocationOnMock.getArgument(0);
      ConnaissanceClient result = null;
      if (_cc != null) {
        result = new ConnaissanceClient();
        result.setId(UUID.randomUUID().toString());
        result.setNom(_cc.getNom());
        result.setPrenom(_cc.getPrenom());
        result.setAdresse(_cc.getAdresse());
        result.setSituationFamiliale(_cc.getSituationFamiliale());
        result.setNombreEnfants(_cc.getNombreEnfants());
      }
      return result;
    });
    // WHEN
    ConnaissanceClient result = service.nouveauClient(ccToSave);
    // THEN
    verify(repository).save(any(ConnaissanceClient.class));
    assertNotNull(result.getId());
    assertEquals("BOUSQUET", result.getNom());
    assertEquals("Philippe", result.getPrenom());
    assertEquals("48 rue bauducheu", result.getAdresse().getLigne1());
    assertTrue(result.getAdresse().getLigne2().isEmpty());
    assertEquals("33800", result.getAdresse().getCodePostal());
    assertEquals("Bordeaux", result.getAdresse().getVille());
    assertEquals(SituationFamiliale.CELIBATAIRE, result.getSituationFamiliale());
    assertEquals(Integer.valueOf(0), result.getNombreEnfants());
  }

  @Test
  public void given_adresse_save_return_ok_with_event() {
    // GIVEN
    Adresse adresse = new Adresse();
    adresse.setVille("Bordeaux");
    adresse.setLigne1("lg1");
    adresse.setLigne2(Optional.ofNullable("lg2"));
    adresse.setCodePostal("33000");
    ConnaissanceClient cc = new ConnaissanceClient("1", "Bousquet", "Philippe", new Adresse("48 rue bauducheu", Optional.empty(), "33800", "Bordeaux"), SituationFamiliale.CELIBATAIRE, 0);
    when(repository.findById(any())).thenReturn(Optional.of(cc));
    when(repository.save(any())).thenAnswer(ConnaissanceClientServiceImplTest::answer);
    // WHEN
    Optional<ConnaissanceClient> result = service.changementAdresse("1", adresse, true);
    // THEN
    assertEquals("lg1", result.get().getAdresse().getLigne1());
    verify(repository).findById(any(String.class));
    verify(repository).save(any(ConnaissanceClient.class));
  }

  @Test
  public void given_adresse_save_return_ok_no_event() {
    // GIVEN
    Adresse adresse = new Adresse();
    adresse.setVille("Bordeaux");
    adresse.setLigne1("lg1");
    adresse.setCodePostal("33000");
    ConnaissanceClient cc = new ConnaissanceClient("1", "Bousquet", "Philippe", new Adresse("48 rue bauducheu", Optional.empty(), "33800", "Bordeaux"), SituationFamiliale.CELIBATAIRE, 0);
    when(repository.findById(any())).thenReturn(Optional.of(cc));
    when(repository.save(any())).thenAnswer(ConnaissanceClientServiceImplTest::answer);
    // WHEN
    Optional<ConnaissanceClient> result = service.changementAdresse("1", adresse, false);
    // THEN
    assertTrue(result.get().getAdresse().getLigne2().isEmpty());
    verify(repository).findById(any());
    verify(repository).save(any(ConnaissanceClient.class));
  }

  @Test
  public void given_situation_save_return_ok_with_event() {
    // GIVEN
    ConnaissanceClient cc = new ConnaissanceClient("1", "Bousquet", "Philippe", new Adresse("48 rue bauducheu", null, "33800", "Bordeaux"), SituationFamiliale.CELIBATAIRE, 0);
    when(repository.findById(any())).thenReturn(Optional.of(cc));
    when(repository.save(any())).thenAnswer(ConnaissanceClientServiceImplTest::answer);
    // WHEN
    Optional<ConnaissanceClient> result = service.changementSituation("1", SituationFamiliale.MARIE, 0);
    // THEN
    assertEquals(SituationFamiliale.MARIE, result.get().getSituationFamiliale());
    verify(repository).findById(any());
    verify(repository).save(any(ConnaissanceClient.class));
  }

  @Test
  public void given_situation_no_client_save_return_ok() {
    // GIVEN
    when(repository.findById(any())).thenReturn(Optional.empty());
    // WHEN
    Optional<ConnaissanceClient> result = service.changementSituation("1", SituationFamiliale.MARIE, 0);
    // THEN
    assertTrue(result.isEmpty());
    verify(repository).findById(any());
    verifyNoMoreInteractions(repository);
  }

  @Test
  public void given_connaissanceclient_findall_return_list() {
    // GIVEN
    Adresse adresse = new Adresse();
    adresse.setVille("Bordeaux");
    adresse.setLigne1("lg1");
    adresse.setLigne2(Optional.ofNullable("lg2"));
    adresse.setCodePostal("33000");
    ConnaissanceClient cc = new ConnaissanceClient("1", "Bousquet", "Philippe", new Adresse("48 rue bauducheu", Optional.empty(), "33800", "Bordeaux"), SituationFamiliale.CELIBATAIRE, 0);
    List<ConnaissanceClient> list = new ArrayList();
    list.add(cc);
    when(repository.findAll()).thenReturn(list);
    // WHEN
    List<ConnaissanceClient> result = service.listerClients();
    // THEN
    assertEquals(1, result.size());
    verify(repository).findAll();
  }

/*
  @Test
  public void test_fonctionnel_enfants_negatif() {
    // GIVEN
    ConnaissanceClient ccToSave = new ConnaissanceClient(null, "Bousquet", "Philippe", null, SituationFamiliale.CELIBATAIRE, -1);
    // WHEN
    service.nouveauClient(ccToSave);
    // THEN
    verify(repository).save(any());
  }
*/
}
