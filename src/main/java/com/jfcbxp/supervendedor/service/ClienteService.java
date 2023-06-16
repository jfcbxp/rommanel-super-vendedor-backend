package com.jfcbxp.supervendedor.service;

import com.jfcbxp.supervendedor.dto.response.ClienteResponse;
import reactor.core.publisher.Flux;

public interface ClienteService {

    Flux<ClienteResponse> buscarCliente(String pesquisa);

}
