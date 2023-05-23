package com.jfcbxp.supervendedor.service;

import com.jfcbxp.supervendedor.dto.response.FaturamentoResponse;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface FaturamentoService {

    Flux<FaturamentoResponse> buscarfaturamento(String codigoVendedor, LocalDate emissao);

    Flux<FaturamentoResponse> buscarfaturamentoMensal(String codigoVendedor);
}
