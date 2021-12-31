package com.sqli.workshop.ddd.connaissance.client.domain;

import com.sqli.workshop.ddd.connaissance.client.domain.enums.SituationFamiliale;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.*;

//@RunWith(SpringRunner.class)
public class ConnaissanceClientTest {

  @Test
  public void test_noargconstructor() {
    ConnaissanceClient cc = new ConnaissanceClient();
    assertNull(cc.getId());
    assertNull(cc.getNom());
    assertNull(cc.getPrenom());
    assertNull(cc.getAdresse());
    assertNull(cc.getSituationFamiliale());
    assertNull(cc.getNom());
  }

  @Test
  public void test_allargsconstructor() {
    ConnaissanceClient cc = new ConnaissanceClient("1","Bousquet","Philippe", new Adresse(), SituationFamiliale.CELIBATAIRE, 0);
    assertNotNull(cc.getId());
    assertNotNull(cc.getNom());
    assertNotNull(cc.getPrenom());
    assertNotNull(cc.getAdresse());
    assertNotNull(cc.getSituationFamiliale());
    assertNotNull(cc.getNom());
  }

}
