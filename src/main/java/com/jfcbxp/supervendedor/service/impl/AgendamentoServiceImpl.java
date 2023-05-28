package com.jfcbxp.supervendedor.service.impl;

import com.jfcbxp.supervendedor.dto.response.AgendamentoResponse;
import com.jfcbxp.supervendedor.dto.response.TotalizadorAgendamentoResponse;
import com.jfcbxp.supervendedor.repository.AgendamentoRepository;
import com.jfcbxp.supervendedor.repository.TotalizadorAgendamentoRepository;
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

    private static final String KEY_CACHE_AGENDAMENTO = "agendamento:";
    private static final String KEY_CACHE_TOTALIZADOR_AGENDAMENTO_DIARIO = "agendamento:totalizador:diario:";
    private static final String KEY_CACHE_TOTALIZADOR_AGENDAMENTO_MENSAL = "agendamento:totalizador:mensal:";
    private final AgendamentoRepository repository;
    private final TotalizadorAgendamentoRepository totalizadorRepository;
    private final RedissonReactiveClient redissonReactiveClient;
    private final ModelMapper mapper;

    @Override
    public Flux<AgendamentoResponse> buscarAgendamentos(String codigoVendedor) {
        var key = KEY_CACHE_AGENDAMENTO.concat(codigoVendedor);
        return getAgendamentoFromCache(key)
                .switchIfEmpty(
                            repository
                                    .findByCodigoVendedorAndDataAgendamentoOrderByHoraInicial(codigoVendedor,LocalDate.now())
                                    .flatMap(agendamento ->   updateAgendamentoCache(key,mapper.map(agendamento, AgendamentoResponse.class)))
                );

    }

    @Override
    public Mono<TotalizadorAgendamentoResponse> buscarTotalizadorDiario(String codigoVendedor) {
        var key = KEY_CACHE_TOTALIZADOR_AGENDAMENTO_DIARIO.concat(codigoVendedor);
        return getTotalizadorFromCache(key)
                .switchIfEmpty(
                        totalizadorRepository
                                .totalizarByCodigoVendedorAndDataAgendamento(codigoVendedor,LocalDate.now(),LocalDate.now())
                                .flatMap(totalizador ->
                                        updateTotalizadorCache(key
                                                ,mapper.map(totalizador, TotalizadorAgendamentoResponse.class)))
                );
    }

    @Override
    public Mono<TotalizadorAgendamentoResponse> buscarTotalizadorMensal(String codigoVendedor) {
        var year = LocalDate.now().getYear();
        var month = LocalDate.now().getMonth();
        var key = KEY_CACHE_TOTALIZADOR_AGENDAMENTO_MENSAL.concat(codigoVendedor);
        return getTotalizadorFromCache(key)
                .switchIfEmpty(
                        totalizadorRepository
                                .totalizarByCodigoVendedorAndDataAgendamento(codigoVendedor,LocalDate.of(year, month, 1),LocalDate.now())
                                .flatMap(totalizador ->
                                        updateTotalizadorCache(key
                                                ,mapper.map(totalizador, TotalizadorAgendamentoResponse.class)))
                );
    }


    private Flux<AgendamentoResponse> getAgendamentoFromCache(String key) {

        RMapReactive<Integer,AgendamentoResponse> mapReactive = redissonReactiveClient
                .getMap(key, new TypedJsonJacksonCodec(Integer.class,AgendamentoResponse.class));
        return mapReactive.readAllValues().flux()
                .filter(agendamentos -> !agendamentos.isEmpty())
                .flatMap(agendamentos -> Flux.fromStream(agendamentos.stream()));
    }


    private Mono<AgendamentoResponse> updateAgendamentoCache(String key, AgendamentoResponse agendamento) {
        RMapReactive<Integer,AgendamentoResponse> mapReactive = redissonReactiveClient
                .getMap(key, new TypedJsonJacksonCodec(Integer.class,AgendamentoResponse.class));
        return mapReactive.fastPut(agendamento.getId(),agendamento).thenReturn(agendamento)
                .doFinally(signalType -> mapReactive.expire(Duration.ofHours(1L)).subscribe());

    }

    private Mono<TotalizadorAgendamentoResponse> getTotalizadorFromCache(String key) {
        RMapReactive<Integer,TotalizadorAgendamentoResponse> mapReactive = redissonReactiveClient
                .getMap(key, new TypedJsonJacksonCodec(Integer.class,TotalizadorAgendamentoResponse.class));
        return mapReactive.get(0);
    }


    private Mono<TotalizadorAgendamentoResponse> updateTotalizadorCache(String key, TotalizadorAgendamentoResponse totalizador) {
        RMapReactive<Integer,TotalizadorAgendamentoResponse> mapReactive = redissonReactiveClient
                .getMap(key, new TypedJsonJacksonCodec(Integer.class,TotalizadorAgendamentoResponse.class));
        return mapReactive.fastPut(0,totalizador)
                .thenReturn(totalizador)
                .doFinally(signalType -> mapReactive.expire(Duration.ofHours(1L)).subscribe());
    }


}
