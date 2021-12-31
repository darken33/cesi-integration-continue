package com.sqli.workshop.ddd.connaissance.client.generated.api.server;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@Controller
@RequestMapping("${openapi.connaissanceClient.base-path:}")
public class ConnaissanceClientASApiController implements ConnaissanceClientASApi {

    private final ConnaissanceClientASApiDelegate delegate;

    public ConnaissanceClientASApiController(@org.springframework.beans.factory.annotation.Autowired(required = false) ConnaissanceClientASApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new ConnaissanceClientASApiDelegate() {});
    }

    @Override
    public ConnaissanceClientASApiDelegate getDelegate() {
        return delegate;
    }

}
