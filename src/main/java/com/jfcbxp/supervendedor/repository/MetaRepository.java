package com.jfcbxp.supervendedor.repository;

import com.jfcbxp.supervendedor.domain.Meta;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface MetaRepository extends ReactiveCrudRepository<Meta,Integer> {

    @Query("SELECT * FROM META_VENDEDOR WHERE CODIGO_VENDEDOR = :codigoVendedor AND :data BETWEEN DATA_INICIAL AND DATA_FINAL")
    Mono<Meta> findByCodigoVendedorAndData(String codigoVendedor, LocalDate data);

}
