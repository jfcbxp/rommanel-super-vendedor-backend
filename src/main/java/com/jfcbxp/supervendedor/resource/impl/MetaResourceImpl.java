package com.jfcbxp.supervendedor.resource.impl;

import com.jfcbxp.supervendedor.dto.response.MetaResponse;
import com.jfcbxp.supervendedor.resource.MetaResource;
import com.jfcbxp.supervendedor.service.MetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/meta")
public class MetaResourceImpl implements MetaResource {
    private final MetaService service;

    @Override
    public ResponseEntity<Mono<MetaResponse>> buscarMeta(@PathVariable String codigoVendedor) {
        return ResponseEntity.ok(service.buscarMeta(codigoVendedor)) ;
    }
}
