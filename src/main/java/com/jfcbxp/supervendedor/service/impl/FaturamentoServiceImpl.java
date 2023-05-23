package com.jfcbxp.supervendedor.service.impl;

import com.jfcbxp.supervendedor.dto.response.FaturamentoResponse;
import com.jfcbxp.supervendedor.repository.FaturamentoRepository;
import com.jfcbxp.supervendedor.service.FaturamentoService;
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
public class FaturamentoServiceImpl implements FaturamentoService {

    private static final String KEY_CACHE_FATURAMENTO = "faturamento:";
    private final FaturamentoRepository repository;
    private final RedissonReactiveClient redissonReactiveClient;
    private final ModelMapper mapper;

    @Override
    public Flux<FaturamentoResponse> buscarfaturamento(String codigoVendedor, LocalDate emissao) {
        var key = KEY_CACHE_FATURAMENTO.concat(codigoVendedor);
        return getFaturamentoFromCache(key)
                .switchIfEmpty(
                            repository
                                    .findByCodigoVendedorAndEmissao(codigoVendedor,emissao)
                                    .flatMap(faturamento ->   updateFaturamentoCache(key,mapper.map(faturamento, FaturamentoResponse.class)))
                );

    }

    @Override
    public Flux<FaturamentoResponse> buscarfaturamentoMensal(String codigoVendedor) {
        var key = KEY_CACHE_FATURAMENTO.concat(codigoVendedor);
        var year = LocalDate.now().getYear();
        var month = LocalDate.now().getMonth();
        return getFaturamentoFromCache(key)
                .switchIfEmpty(
                        Flux.range(1, LocalDate.now().lengthOfMonth())
                                .flatMap(day ->
                                        repository
                                                .findByCodigoVendedorAndEmissao(codigoVendedor,LocalDate.of(year, month, day))
                                                .flatMap(faturamento ->   updateFaturamentoCache(key,mapper.map(faturamento, FaturamentoResponse.class)))
                                )
                );
    }

    private Flux<FaturamentoResponse> getFaturamentoFromCache(String key) {

        RMapReactive<Integer,FaturamentoResponse> mapReactive = redissonReactiveClient
                .getMap(key, new TypedJsonJacksonCodec(Integer.class,FaturamentoResponse.class));
        return mapReactive.readAllValues().flux()
                .filter(agendamentos -> !agendamentos.isEmpty())
                .flatMap(agendamentos -> Flux.fromStream(agendamentos.stream()));
    }


    private Mono<FaturamentoResponse> updateFaturamentoCache(String key, FaturamentoResponse faturamento) {
        RMapReactive<Integer,FaturamentoResponse> mapReactive = redissonReactiveClient
                .getMap(key, new TypedJsonJacksonCodec(Integer.class,FaturamentoResponse.class));
        return mapReactive.fastPut(faturamento.getId(),faturamento).thenReturn(faturamento)
                .doFinally(signalType -> mapReactive.expire(Duration.ofHours(1L)).subscribe());

    }

}
