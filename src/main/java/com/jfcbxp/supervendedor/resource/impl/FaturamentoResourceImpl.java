package com.jfcbxp.supervendedor.resource.impl;

import com.jfcbxp.supervendedor.dto.response.FaturamentoProgressResponse;
import com.jfcbxp.supervendedor.dto.response.FaturamentoResponse;
import com.jfcbxp.supervendedor.resource.FaturamentoResource;
import com.jfcbxp.supervendedor.service.FaturamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/faturamento")
public class FaturamentoResourceImpl implements FaturamentoResource {
    private final FaturamentoService service;

    @Override
    public ResponseEntity<Flux<FaturamentoResponse>> buscarFaturamento(String codigoVendedor,LocalDate emissao) {
        return ResponseEntity.ok(service.buscarFaturamento(codigoVendedor,emissao)) ;
    }

    @Override
    public ResponseEntity<Flux<FaturamentoProgressResponse>> buscarProgresso(String codigoVendedor) {
        return ResponseEntity.ok(service.buscarProgresso(codigoVendedor)) ;
    }

}
