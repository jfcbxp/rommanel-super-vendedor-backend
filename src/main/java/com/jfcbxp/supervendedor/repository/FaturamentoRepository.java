package com.jfcbxp.supervendedor.repository;

import com.jfcbxp.supervendedor.domain.Faturamento;
import com.jfcbxp.supervendedor.dto.response.FaturamentoProgressResponse;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface FaturamentoRepository extends ReactiveCrudRepository<Faturamento,Integer> {
    Flux<Faturamento> findByCodigoVendedorAndEmissao(String codigoVendedor, LocalDate emissao);

    @Query("select emissao as periodo,count(*) AS total from FATURAMENTO_VENDEDOR where CODIGO_VENDEDOR = :codigoVendedor and EMISSAO between :dataInicial and :dataFinal GROUP BY EMISSAO ORDER BY EMISSAO")
    Flux<FaturamentoProgressResponse> contarByCodigoVendedorAndEmissao(String codigoVendedor, LocalDate dataInicial, LocalDate dataFinal);

}
