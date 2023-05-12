package com.jfcbxp.supervendedor.service.impl;

import com.jfcbxp.supervendedor.domain.Agendamento;
import com.jfcbxp.supervendedor.repository.AgendamentoRepository;
import com.jfcbxp.supervendedor.service.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AgendamentoServiceImpl implements AgendamentoService {

    public static final String KEY_CACHE_AGENDAMENTO = "agendamento:";
    private final AgendamentoRepository repository;

    private final RedissonReactiveClient redissonReactiveClient;

    @Override
    public Flux<Agendamento> buscarAgendamentos(String codigoVendedor) {
        var year = LocalDate.now().getYear();
        var month = LocalDate.now().getMonth();

        return Flux.range(1, LocalDate.now().lengthOfMonth())
                .flatMap(day ->
                        getFromCache(codigoVendedor)
                                .switchIfEmpty(
                                        repository
                                                .findByCodigoVendedorAndDataAgendamento(codigoVendedor,LocalDate.of(year, month, day))
                                                .flatMap(agendamento ->   updateCache(codigoVendedor,agendamento))
                                                )
                                );
    }


    private Flux<Agendamento> getFromCache(String key) {

        RMapReactive<Integer,Agendamento> mapReactive = redissonReactiveClient.getMap(KEY_CACHE_AGENDAMENTO.concat(key), new TypedJsonJacksonCodec(Integer.class,Agendamento.class));
        return mapReactive.readAllValues().flux()
                .filter(agendamentos -> !agendamentos.isEmpty())
                .flatMap(agendamentos -> Flux.fromStream(agendamentos.stream()));


    }


    private Mono<Agendamento> updateCache(String key, Agendamento agendamento) {
        RMapReactive<Integer,Agendamento> mapReactive = redissonReactiveClient.getMap(KEY_CACHE_AGENDAMENTO.concat(key), new TypedJsonJacksonCodec(Integer.class,Agendamento.class));
        mapReactive.expire(Duration.ofHours(1L)).then().subscribe();

        return mapReactive.fastPut(agendamento.getId(),agendamento).thenReturn(agendamento);
    }


}
