package com.sqli.pbousquet.testapi.api.impl;

import com.sqli.pbousquet.testapi.api.HelloApi;
import com.sqli.pbousquet.testapi.dto.HelloDto;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HelloApiImplTest {

    private final HelloApi helloApi = new HelloApiImpl();

    @Test
    public void getHello_must_return_Hello_World() {
        ResponseEntity<HelloDto> result = helloApi.helloUsingGET1();
        Assert.assertTrue(HttpStatus.OK.equals(result.getStatusCode()));
        Assert.assertTrue("Hello World".equals(result.getBody().getMessage()));
    }

    @Test
    public void getHello_Philippe_must_return_Hello_Philippe() {
        ResponseEntity<HelloDto> result = helloApi.helloUsingGET("Philippe");
        Assert.assertTrue(HttpStatus.OK.equals(result.getStatusCode()));
        Assert.assertTrue("Hello Philippe".equals(result.getBody().getMessage()));
    }
}
