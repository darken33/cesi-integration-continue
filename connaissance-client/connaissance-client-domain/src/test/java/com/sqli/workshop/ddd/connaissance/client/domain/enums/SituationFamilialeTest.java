package com.sqli.workshop.ddd.connaissance.client.domain.enums;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.*;

//@RunWith(SpringRunner.class)
public class SituationFamilialeTest {

  @Test
  public void given_value_1_enum_is_CELIBATAIRE() {
    assertEquals(SituationFamiliale.CELIBATAIRE, SituationFamiliale.fromValue("1"));
    assertEquals("1", SituationFamiliale.CELIBATAIRE.toString());
  }

  @Test
  public void given_value_2_enum_is_MARIE() {
    assertEquals(SituationFamiliale.MARIE, SituationFamiliale.fromValue("2"));
    assertEquals("2", SituationFamiliale.MARIE.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void given_value_3_enum_throws_exception() {
    SituationFamiliale test = SituationFamiliale.fromValue("3");
  }
}
