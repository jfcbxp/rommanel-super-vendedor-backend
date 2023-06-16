package com.jfcbxp.supervendedor.repository;

import com.jfcbxp.supervendedor.domain.Cliente;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ClienteRepository extends ReactiveCrudRepository<Cliente,Integer> {
    @Query("SELECT TOP 10 * FROM CLIENTE WHERE CODIGO = :pesquisa OR CGC = :pesquisa OR NOME LIKE CONCAT('%', :pesquisa,'%') OR TELEFONE LIKE CONCAT('%', :pesquisa) OR EMAIL LIKE  CONCAT(:pesquisa,'%')  ORDER BY NOME")
    Flux<Cliente> findByPesquisa(String pesquisa);
}
