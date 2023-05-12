package com.jfcbxp.supervendedor.service;

import com.jfcbxp.supervendedor.domain.Agendamento;
import reactor.core.publisher.Flux;

public interface AgendamentoService {

    Flux<Agendamento> buscarAgendamentos(String codigoVendedor);
}
