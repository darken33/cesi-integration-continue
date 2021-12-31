package com.sqli.workshop.ddd.connaissance.client.generated.api.server;

import com.sqli.workshop.ddd.connaissance.client.generated.api.model.ApiErrorResponseDto;
import com.sqli.workshop.ddd.connaissance.client.generated.api.model.ConnaissanceClientDto;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A delegate to be called by the {@link ConnaissanceClientASApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */

public interface ConnaissanceClientASApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * @see ConnaissanceClientASApi#saveConnaissanceClientAsync
     */
    default ResponseEntity<ApiErrorResponseDto> saveConnaissanceClientAsync(ConnaissanceClientDto connaissanceClientDto) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    ApiUtil.setExampleResponse(request, "application/json", "{  \"path\" : \"path\",  \"details\" : [ \"details\", \"details\" ],  \"status\" : 0}");
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
