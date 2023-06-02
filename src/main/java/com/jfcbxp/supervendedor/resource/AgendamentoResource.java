package com.jfcbxp.supervendedor.resource;

import com.jfcbxp.supervendedor.dto.response.AgendamentoResponse;
import com.jfcbxp.supervendedor.dto.response.TotalizadorAgendamentoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AgendamentoResource {

    @GetMapping(value = "/{codigoVendedor}")
    ResponseEntity<Flux<AgendamentoResponse>> buscarAgendamentos(@PathVariable String codigoVendedor);
    @GetMapping(value = "/totalizador/diario/{codigoVendedor}")
    ResponseEntity<Mono<TotalizadorAgendamentoResponse>> buscarTotalizadorDiario(@PathVariable String codigoVendedor);
    @GetMapping(value = "/totalizador/mensal/{codigoVendedor}")
    ResponseEntity<Mono<TotalizadorAgendamentoResponse>> buscarTotalizadorMensal(@PathVariable String codigoVendedor);

}
