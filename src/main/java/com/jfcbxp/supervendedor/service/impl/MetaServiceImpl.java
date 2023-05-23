package com.jfcbxp.supervendedor.service.impl;

import com.jfcbxp.supervendedor.dto.response.MetaResponse;
import com.jfcbxp.supervendedor.repository.MetaRepository;
import com.jfcbxp.supervendedor.service.MetaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MetaServiceImpl implements MetaService {

    private static final String KEY_CACHE_META = "meta:";
    private final MetaRepository repository;
    private final RedissonReactiveClient redissonReactiveClient;
    private final ModelMapper mapper;

    @Override
    public Mono<MetaResponse> buscarMeta(String codigoVendedor) {
        var key = KEY_CACHE_META.concat(codigoVendedor);
        return getTotalizadorFromCache(key)
                .switchIfEmpty(
                        repository
                                .findByCodigoVendedorAndData(codigoVendedor,LocalDate.now())
                                .flatMap(meta ->
                                        updateTotalizadorCache(key
                                                ,mapper.map(meta, MetaResponse.class)))
                );
    }

    private Mono<MetaResponse> getTotalizadorFromCache(String key) {
        RMapReactive<Integer,MetaResponse> mapReactive = redissonReactiveClient
                .getMap(key, new TypedJsonJacksonCodec(Integer.class,MetaResponse.class));
        return mapReactive.get(0);
    }


    private Mono<MetaResponse> updateTotalizadorCache(String key, MetaResponse meta) {
        RMapReactive<Integer,MetaResponse> mapReactive = redissonReactiveClient
                .getMap(key, new TypedJsonJacksonCodec(Integer.class,MetaResponse.class));
        return mapReactive.fastPut(0,meta)
                .thenReturn(meta)
                .doFinally(signalType -> mapReactive.expire(Duration.ofHours(1L)).subscribe());
    }


}
