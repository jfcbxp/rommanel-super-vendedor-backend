package com.jfcbxp.supervendedor.service;

import com.jfcbxp.supervendedor.dto.response.FaturamentoProgressResponse;
import com.jfcbxp.supervendedor.dto.response.FaturamentoResponse;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface FaturamentoService {

    Flux<FaturamentoResponse> buscarFaturamento(String codigoVendedor, LocalDate emissao);

    Flux<FaturamentoProgressResponse> buscarProgresso(String codigoVendedor);
}
