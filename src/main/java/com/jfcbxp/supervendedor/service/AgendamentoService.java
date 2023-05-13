package com.jfcbxp.supervendedor.service;

import com.jfcbxp.supervendedor.dto.response.AgendamentoResponse;
import reactor.core.publisher.Flux;

public interface AgendamentoService {

    Flux<AgendamentoResponse> buscarAgendamentos(String codigoVendedor);
}
