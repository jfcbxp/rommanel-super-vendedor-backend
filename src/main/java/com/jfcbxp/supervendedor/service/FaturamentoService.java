package com.jfcbxp.supervendedor.service;

import com.jfcbxp.supervendedor.dto.response.FaturamentoProgressResponse;
import com.jfcbxp.supervendedor.dto.response.FaturamentoResponse;
import com.jfcbxp.supervendedor.dto.response.TotalizadorFaturamentoResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface FaturamentoService {

    Flux<FaturamentoResponse> buscarFaturamento(String codigoVendedor, LocalDate emissao);
    Flux<FaturamentoProgressResponse> buscarProgresso(String codigoVendedor);
    Mono<TotalizadorFaturamentoResponse> buscarTotalizadorDiario(String codigoVendedor, LocalDate emissao);
    Mono<TotalizadorFaturamentoResponse> buscarTotalizadorMensal(String codigoVendedor);
}
