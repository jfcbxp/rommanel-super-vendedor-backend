package com.jfcbxp.supervendedor.repository;

import com.jfcbxp.supervendedor.domain.Agendamento;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface AgendamentoRepository extends ReactiveCrudRepository<Agendamento,Integer> {
    Flux<Agendamento> findByCodigoVendedorAndDataAgendamento(String codigoVendedor, LocalDate dataAgendamento);

}
