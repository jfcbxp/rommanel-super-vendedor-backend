package com.jfcbxp.supervendedor.resource;

import com.jfcbxp.supervendedor.dto.response.AgendamentoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;

public interface AgendamentoResource {

    @GetMapping(value = "/{codigoVendedor}")
    ResponseEntity<Flux<AgendamentoResponse>> buscarAgendamentos(@PathVariable String codigoVendedor);

}
