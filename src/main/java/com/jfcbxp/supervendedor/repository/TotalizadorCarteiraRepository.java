package com.jfcbxp.supervendedor.repository;

import com.jfcbxp.supervendedor.domain.TotalizadorCarteira;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TotalizadorCarteiraRepository extends ReactiveCrudRepository<TotalizadorCarteira,String> {

    @Query(" select SUM(total) AS total, SUM(ativos) AS ativos, SUM(preInativos) AS preInativos, SUM(inativos) AS inativos " +
            " from TOTALIZADOR_CARTEIRA  where CODIGO = :codigo and CODIGO_VENDEDOR = :codigoVendedor ")
    Mono<TotalizadorCarteira> totalizarByCodigoAndCodigoVendedor(String codigo,String codigoVendedor);

}
