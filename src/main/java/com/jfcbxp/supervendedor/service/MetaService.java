package com.jfcbxp.supervendedor.service;

import com.jfcbxp.supervendedor.dto.response.MetaResponse;
import reactor.core.publisher.Mono;

public interface MetaService {

    Mono<MetaResponse> buscarMeta(String codigoVendedor);

}
