package com.jfcbxp.supervendedor.resource;

import com.jfcbxp.supervendedor.dto.response.FaturamentoProgressResponse;
import com.jfcbxp.supervendedor.dto.response.FaturamentoResponse;
import com.jfcbxp.supervendedor.dto.response.TotalizadorFaturamentoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface FaturamentoResource {

    @GetMapping(value = "/{codigoVendedor}/{emissao}")
    ResponseEntity<Flux<FaturamentoResponse>> buscarFaturamento(@PathVariable String codigoVendedor,@PathVariable LocalDate emissao);
    @GetMapping(value = "/progresso/{codigoVendedor}")
    ResponseEntity<Flux<FaturamentoProgressResponse>> buscarProgresso(@PathVariable String codigoVendedor);
    @GetMapping(value = {"/totalizador/diario/{codigoVendedor}","/totalizador/diario/{codigoVendedor}/{emissao}"})
    ResponseEntity<Mono<TotalizadorFaturamentoResponse>> buscarTotalizadorDiario(@PathVariable String codigoVendedor,@PathVariable(required = false) LocalDate emissao);
    @GetMapping(value = "/totalizador/mensal/{codigoVendedor}")
    ResponseEntity<Mono<TotalizadorFaturamentoResponse>> buscarTotalizadorMensal(@PathVariable String codigoVendedor);


}
