package com.sqli.pbousquet.testapi.api.impl;

import com.sqli.pbousquet.testapi.api.GoodbyeApi;
import com.sqli.pbousquet.testapi.dto.GoodbyeDto;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GoodbyeApiImplTest {

    private final GoodbyeApi goodbyeApi = new GoodbyeApiImpl();

    @Test
    public void getGoodbye_must_return_Goodbye_World() {
        ResponseEntity<GoodbyeDto> result = goodbyeApi.goodbyeUsingGET1();
        Assert.assertTrue(HttpStatus.OK.equals(result.getStatusCode()));
        Assert.assertTrue("Goodbye World".equals(result.getBody().getMessage()));
    }

    @Test
    public void getGoodbye_Philippe_must_return_Goodbye_Philippe() {
        ResponseEntity<GoodbyeDto> result = goodbyeApi.goodbyeUsingGET("Philippe");
        Assert.assertTrue(HttpStatus.OK.equals(result.getStatusCode()));
        Assert.assertTrue("Goodbye Philippe".equals(result.getBody().getMessage()));
    }

}
