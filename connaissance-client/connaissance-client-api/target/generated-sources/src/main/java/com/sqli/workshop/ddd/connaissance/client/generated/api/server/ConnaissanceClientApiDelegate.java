package com.sqli.workshop.ddd.connaissance.client.generated.api.server;

import com.sqli.workshop.ddd.connaissance.client.generated.api.model.AdresseDto;
import com.sqli.workshop.ddd.connaissance.client.generated.api.model.ApiErrorResponseDto;
import com.sqli.workshop.ddd.connaissance.client.generated.api.model.ConnaissanceClientDto;
import com.sqli.workshop.ddd.connaissance.client.generated.api.model.SituationDto;
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
 * A delegate to be called by the {@link ConnaissanceClientApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */

public interface ConnaissanceClientApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * @see ConnaissanceClientApi#changerAdresse
     */
    default ResponseEntity<ConnaissanceClientDto> changerAdresse(String id,
        AdresseDto adresseDto) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    ApiUtil.setExampleResponse(request, "application/json", "{  \"ligne2\" : \"ligne2\",  \"ville\" : \"ville\",  \"ligne1\" : \"ligne1\",  \"situationFamilialle\" : \"situationFamilialle\",  \"id\" : \"id\",  \"codePostal\" : \"codePostal\",  \"nom\" : \"nom\",  \"prenom\" : \"prenom\",  \"nombreEnfants\" : 0}");
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * @see ConnaissanceClientApi#changerSituation
     */
    default ResponseEntity<ConnaissanceClientDto> changerSituation(String id,
        SituationDto situationDto) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    ApiUtil.setExampleResponse(request, "application/json", "{  \"ligne2\" : \"ligne2\",  \"ville\" : \"ville\",  \"ligne1\" : \"ligne1\",  \"situationFamilialle\" : \"situationFamilialle\",  \"id\" : \"id\",  \"codePostal\" : \"codePostal\",  \"nom\" : \"nom\",  \"prenom\" : \"prenom\",  \"nombreEnfants\" : 0}");
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * @see ConnaissanceClientApi#getConnaissanceClient
     */
    default ResponseEntity<ConnaissanceClientDto> getConnaissanceClient(String id) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    ApiUtil.setExampleResponse(request, "application/json", "{  \"ligne2\" : \"ligne2\",  \"ville\" : \"ville\",  \"ligne1\" : \"ligne1\",  \"situationFamilialle\" : \"situationFamilialle\",  \"id\" : \"id\",  \"codePostal\" : \"codePostal\",  \"nom\" : \"nom\",  \"prenom\" : \"prenom\",  \"nombreEnfants\" : 0}");
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * @see ConnaissanceClientApi#getConnaissanceClients
     */
    default ResponseEntity<List<ConnaissanceClientDto>> getConnaissanceClients() {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    ApiUtil.setExampleResponse(request, "application/json", "{  \"ligne2\" : \"ligne2\",  \"ville\" : \"ville\",  \"ligne1\" : \"ligne1\",  \"situationFamilialle\" : \"situationFamilialle\",  \"id\" : \"id\",  \"codePostal\" : \"codePostal\",  \"nom\" : \"nom\",  \"prenom\" : \"prenom\",  \"nombreEnfants\" : 0}");
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * @see ConnaissanceClientApi#saveConnaissanceClient
     */
    default ResponseEntity<ConnaissanceClientDto> saveConnaissanceClient(ConnaissanceClientDto connaissanceClientDto) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    ApiUtil.setExampleResponse(request, "application/json", "{  \"ligne2\" : \"ligne2\",  \"ville\" : \"ville\",  \"ligne1\" : \"ligne1\",  \"situationFamilialle\" : \"situationFamilialle\",  \"id\" : \"id\",  \"codePostal\" : \"codePostal\",  \"nom\" : \"nom\",  \"prenom\" : \"prenom\",  \"nombreEnfants\" : 0}");
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
