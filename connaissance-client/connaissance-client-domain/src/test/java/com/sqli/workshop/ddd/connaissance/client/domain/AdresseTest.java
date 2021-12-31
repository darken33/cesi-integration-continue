package com.sqli.workshop.ddd.connaissance.client.domain;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.*;

//@Ignore
//@RunWith(SpringRunner.class)
public class AdresseTest {

  @Test
  public void test_noargconstructor() {
    Adresse adr = new Adresse();
    assertNull(adr.getLigne1());
    assertNotNull(adr.getLigne2());
    assertTrue(adr.getLigne2().isEmpty());
    assertNull(adr.getCodePostal());
    assertNull(adr.getVille());
  }

  @Test
  public void test_allargsconstructor() {
    Adresse adr = new Adresse("lg1", Optional.of("lg2"), "33800", "Bordeaux");
    assertEquals("lg1", adr.getLigne1());
    assertEquals("lg2", adr.getLigne2().get());
    assertEquals("33800", adr.getCodePostal());
    assertEquals("Bordeaux", adr.getVille());
  }

}
