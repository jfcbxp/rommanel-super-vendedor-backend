package com.jfcbxp.supervendedor.service;

import com.jfcbxp.supervendedor.dto.request.AgendamentoRequest;
import com.jfcbxp.supervendedor.dto.response.AgendamentoResponse;
import com.jfcbxp.supervendedor.dto.response.TotalizadorAgendamentoResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AgendamentoService {

    Flux<AgendamentoResponse> buscarAgendamentos(String codigoVendedor);

    Mono<TotalizadorAgendamentoResponse> buscarTotalizadorDiario(String codigoVendedor);

    Mono<TotalizadorAgendamentoResponse> buscarTotalizadorMensal(String codigoVendedor);

    Mono<Void> atualizar(AgendamentoRequest agendamento);
}
