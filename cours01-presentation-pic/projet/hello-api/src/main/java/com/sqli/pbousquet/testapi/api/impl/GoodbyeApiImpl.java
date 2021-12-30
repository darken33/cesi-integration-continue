package com.sqli.pbousquet.testapi.api.impl;

import com.sqli.pbousquet.testapi.api.GoodbyeApi;
import com.sqli.pbousquet.testapi.dto.GoodbyeDto;
import com.sqli.pbousquet.testapi.dto.HelloDto;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Goodbye", description = "the Goodbye API", tags = {"goodbye"})
public class GoodbyeApiImpl implements GoodbyeApi {

    @Override
    public ResponseEntity<GoodbyeDto> goodbyeUsingGET1() {
        GoodbyeDto result = new GoodbyeDto();
        result.setMessage("Goodbye World");
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<GoodbyeDto> goodbyeUsingGET(String name) {
        GoodbyeDto result = new GoodbyeDto();
        result.setMessage("Goodbye "+name);
        return ResponseEntity.ok(result);
    }

}
