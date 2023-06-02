package com.jfcbxp.supervendedor.resource;

import com.jfcbxp.supervendedor.dto.response.CarteiraResponse;
import com.jfcbxp.supervendedor.dto.response.TotalizadorCarteiraResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CarteiraResource {

    @GetMapping(value = "/{codigo}/{codigoVendedor}")
    ResponseEntity<Flux<CarteiraResponse>> buscarCarteira(@PathVariable String codigo, @PathVariable String codigoVendedor);
    @GetMapping(value = "/totalizador/{codigo}/{codigoVendedor}")
    ResponseEntity<Mono<TotalizadorCarteiraResponse>> buscarTotalizador(@PathVariable String codigo, @PathVariable String codigoVendedor);

}
