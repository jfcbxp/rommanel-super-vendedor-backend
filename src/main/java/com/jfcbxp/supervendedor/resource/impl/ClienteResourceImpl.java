package com.jfcbxp.supervendedor.resource.impl;

import com.jfcbxp.supervendedor.dto.response.ClienteResponse;
import com.jfcbxp.supervendedor.resource.ClienteResource;
import com.jfcbxp.supervendedor.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/cliente")
public class ClienteResourceImpl implements ClienteResource {
    private final ClienteService service;

    @Override
    public ResponseEntity<Flux<ClienteResponse>> buscarCliente(@PathVariable String pesquisa) {
        return ResponseEntity.ok(service.buscarCliente(pesquisa)) ;
    }
}
