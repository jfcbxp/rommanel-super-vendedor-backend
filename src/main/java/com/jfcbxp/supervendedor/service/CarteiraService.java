package com.jfcbxp.supervendedor.service;

import com.jfcbxp.supervendedor.dto.response.CarteiraResponse;
import com.jfcbxp.supervendedor.dto.response.TotalizadorCarteiraResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CarteiraService {

    Flux<CarteiraResponse> buscarCarteira(String codigo,String codigoVendedor);
    Mono<TotalizadorCarteiraResponse> buscarTotalizador(String codigo,String codigoVendedor);

}
