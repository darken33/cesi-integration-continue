package com.sqli.workshop.ddd.connaissance.client.generated.api.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Controller
@RequestMapping("${openapi.connaissanceClient.base-path:}")
public class ConnaissanceClientApiController implements ConnaissanceClientApi {

    private final ConnaissanceClientApiDelegate delegate;

    public ConnaissanceClientApiController(@org.springframework.beans.factory.annotation.Autowired(required = false) ConnaissanceClientApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new ConnaissanceClientApiDelegate() {});
    }

    @Override
    public ConnaissanceClientApiDelegate getDelegate() {
        return delegate;
    }

}
