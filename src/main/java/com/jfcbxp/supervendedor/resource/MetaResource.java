package com.jfcbxp.supervendedor.resource;

import com.jfcbxp.supervendedor.dto.response.MetaResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

public interface MetaResource {

    @GetMapping(value = "/{codigoVendedor}")
    ResponseEntity<Mono<MetaResponse>> buscarMeta(@PathVariable String codigoVendedor);

}
