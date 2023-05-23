package com.jfcbxp.supervendedor.repository;

import com.jfcbxp.supervendedor.domain.Faturamento;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface FaturamentoRepository extends ReactiveCrudRepository<Faturamento,Integer> {
    Flux<Faturamento> findByCodigoVendedorAndEmissao(String codigoVendedor, LocalDate emissao);

}
