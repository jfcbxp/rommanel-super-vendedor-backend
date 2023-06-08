package com.jfcbxp.supervendedor.resource.impl;

import com.jfcbxp.supervendedor.dto.request.AgendamentoRequest;
import com.jfcbxp.supervendedor.dto.response.AgendamentoResponse;
import com.jfcbxp.supervendedor.dto.response.TotalizadorAgendamentoResponse;
import com.jfcbxp.supervendedor.resource.AgendamentoResource;
import com.jfcbxp.supervendedor.service.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/agendamento")
public class AgendamentoResourceImpl implements AgendamentoResource {
    private final AgendamentoService service;

    @Override
    public ResponseEntity<Flux<AgendamentoResponse>> buscarAgendamentos(String codigoVendedor) {
        return ResponseEntity.ok(service.buscarAgendamentos(codigoVendedor)) ;
    }

    @Override
    public ResponseEntity<Mono<TotalizadorAgendamentoResponse>> buscarTotalizadorDiario(String codigoVendedor) {
        return ResponseEntity.ok(service.buscarTotalizadorDiario(codigoVendedor)) ;
    }

    @Override
    public ResponseEntity<Mono<TotalizadorAgendamentoResponse>> buscarTotalizadorMensal(String codigoVendedor) {
        return ResponseEntity.ok(service.buscarTotalizadorMensal(codigoVendedor)) ;
    }

    @Override
    public ResponseEntity<Mono<Void>> atualizar(AgendamentoRequest agendamento) {
        return ResponseEntity.ok(service.atualizar(agendamento)) ;
    }
}
