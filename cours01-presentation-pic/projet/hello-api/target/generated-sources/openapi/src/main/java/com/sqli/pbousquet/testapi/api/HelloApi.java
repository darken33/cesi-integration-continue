/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (4.1.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.sqli.pbousquet.testapi.api;

import com.sqli.pbousquet.testapi.dto.HelloDto;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2021-12-14T17:48:52.683+01:00[Europe/Paris]")

@Validated
@Api(value = "Hello", description = "the Hello API")
@RequestMapping("")
public interface HelloApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }
    @ApiOperation(value = "Saluer une personne en particulier", nickname = "helloUsingGET", notes = "", response = HelloDto.class, tags={ "hello", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = HelloDto.class),
        @ApiResponse(code = 400, message = "Mauvaise requête, 123 n'est pas une valeurs valide"),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/v1/hello/{name}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<HelloDto> helloUsingGET(@ApiParam(value = "Nom de la personne à saluer",required=true) @PathVariable("name") String name) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    ApiUtil.setExampleResponse(request, "application/json", "{  \"message\" : \"message\"}");
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    @ApiOperation(value = "Saluer le monde", nickname = "helloUsingGET1", notes = "", response = HelloDto.class, tags={ "hello", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = HelloDto.class),
        @ApiResponse(code = 401, message = "Unauthorized"),
        @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/v1/hello",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<HelloDto> helloUsingGET1() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    ApiUtil.setExampleResponse(request, "application/json", "{  \"message\" : \"message\"}");
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
