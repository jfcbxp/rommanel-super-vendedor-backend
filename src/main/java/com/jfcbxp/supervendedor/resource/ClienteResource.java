package com.jfcbxp.supervendedor.resource;

import com.jfcbxp.supervendedor.dto.response.ClienteResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;

public interface ClienteResource {

    @GetMapping(value = "/{pesquisa}")
    ResponseEntity<Flux<ClienteResponse>> buscarCliente(@PathVariable String pesquisa);

}
