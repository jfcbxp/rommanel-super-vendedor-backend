package com.jfcbxp.supervendedor.repository;

import com.jfcbxp.supervendedor.domain.TotalizadorAgendamento;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface TotalizadorAgendamentoRepository extends ReactiveCrudRepository<TotalizadorAgendamento,String> {

    @Query(" select SUM(total) AS total, SUM(previstos) AS previstos, SUM(faltas) AS faltas, SUM(chegadas) AS chegadas " +
            " from TOTALIZADOR_AGENDAMENTO  where CODIGO_VENDEDOR = :codigoVendedor " +
            " and DATA_AGENDAMENTO between :dataInicial and :dataFinal ")
    Mono<TotalizadorAgendamento> totalizarByCodigoVendedorAndDataAgendamento(String codigoVendedor, LocalDate dataInicial, LocalDate dataFinal);

}
