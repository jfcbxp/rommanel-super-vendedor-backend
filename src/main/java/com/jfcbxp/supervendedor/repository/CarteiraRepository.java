package com.jfcbxp.supervendedor.repository;

import com.jfcbxp.supervendedor.domain.Carteira;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CarteiraRepository extends ReactiveCrudRepository<Carteira,Integer> {
    Flux<Carteira> findByCodigoAndCodigoVendedor(String codigo,String codigoVendedor);

}
