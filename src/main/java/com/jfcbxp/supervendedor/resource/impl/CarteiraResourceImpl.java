package com.jfcbxp.supervendedor.resource.impl;

import com.jfcbxp.supervendedor.dto.response.CarteiraResponse;
import com.jfcbxp.supervendedor.dto.response.TotalizadorCarteiraResponse;
import com.jfcbxp.supervendedor.resource.CarteiraResource;
import com.jfcbxp.supervendedor.service.CarteiraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/carteira")
public class CarteiraResourceImpl implements CarteiraResource {
    private final CarteiraService service;

    @Override
    public ResponseEntity<Flux<CarteiraResponse>> buscarCarteira(String codigo,String codigoVendedor) {
        return ResponseEntity.ok(service.buscarCarteira(codigo,codigoVendedor));
    }

    @Override
    public ResponseEntity<Mono<TotalizadorCarteiraResponse>> buscarTotalizador(String codigo, String codigoVendedor) {
        return ResponseEntity.ok(service.buscarTotalizador(codigo,codigoVendedor)) ;
    }


}
