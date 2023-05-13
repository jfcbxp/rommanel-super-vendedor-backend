package com.jfcbxp.supervendedor.service.impl;

import com.jfcbxp.supervendedor.dto.response.AgendamentoResponse;
import com.jfcbxp.supervendedor.repository.AgendamentoRepository;
import com.jfcbxp.supervendedor.service.AgendamentoService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper mapper;

    @Override
    public Flux<AgendamentoResponse> buscarAgendamentos(String codigoVendedor) {
        var year = LocalDate.now().getYear();
        var month = LocalDate.now().getMonth();

        return getFromCache(codigoVendedor)
                .switchIfEmpty(
                        Flux.range(1, LocalDate.now().lengthOfMonth())
                                .flatMap(day ->
                                        repository
                                                .findByCodigoVendedorAndDataAgendamento(codigoVendedor,LocalDate.of(year, month, day))
                                                .flatMap(agendamento ->   updateCache(codigoVendedor,mapper.map(agendamento, AgendamentoResponse.class)))
                                )
                );

    }


    private Flux<AgendamentoResponse> getFromCache(String key) {

        RMapReactive<Integer,AgendamentoResponse> mapReactive = redissonReactiveClient.getMap(KEY_CACHE_AGENDAMENTO.concat(key), new TypedJsonJacksonCodec(Integer.class,AgendamentoResponse.class));
        return mapReactive.readAllValues().flux()
                .filter(agendamentos -> !agendamentos.isEmpty())
                .flatMap(agendamentos -> Flux.fromStream(agendamentos.stream()));


    }


    private Mono<AgendamentoResponse> updateCache(String key, AgendamentoResponse agendamento) {
        RMapReactive<Integer,AgendamentoResponse> mapReactive = redissonReactiveClient.getMap(KEY_CACHE_AGENDAMENTO.concat(key), new TypedJsonJacksonCodec(Integer.class,AgendamentoResponse.class));
        mapReactive.expire(Duration.ofHours(1L)).then().subscribe();

        return mapReactive.fastPut(agendamento.getId(),agendamento).thenReturn(agendamento);
    }


}
