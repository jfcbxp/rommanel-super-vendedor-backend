package com.jfcbxp.supervendedor.resource;

import com.jfcbxp.supervendedor.dto.response.FaturamentoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface FaturamentoResource {

    @GetMapping(value = "/{codigoVendedor}/{emissao}")
    ResponseEntity<Flux<FaturamentoResponse>> buscarFaturamento(@PathVariable String codigoVendedor,@PathVariable LocalDate emissao);

}
