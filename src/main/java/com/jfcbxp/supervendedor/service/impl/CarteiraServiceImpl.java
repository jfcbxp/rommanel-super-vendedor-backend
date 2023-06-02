package com.jfcbxp.supervendedor.service.impl;

import com.jfcbxp.supervendedor.dto.response.CarteiraResponse;
import com.jfcbxp.supervendedor.dto.response.TotalizadorCarteiraResponse;
import com.jfcbxp.supervendedor.repository.CarteiraRepository;
import com.jfcbxp.supervendedor.repository.TotalizadorCarteiraRepository;
import com.jfcbxp.supervendedor.service.CarteiraService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class CarteiraServiceImpl implements CarteiraService {
    private static final String KEY_CACHE_CARTEIRA = "carteira:";
    private static final String KEY_CACHE_TOTALIZADOR_CARTEIRA = "carteira:totalizador:";
    private final CarteiraRepository repository;
    private final TotalizadorCarteiraRepository totalizadorRepository;
    private final RedissonReactiveClient redissonReactiveClient;
    private final ModelMapper mapper;

    @Override
    public Flux<CarteiraResponse> buscarCarteira(String codigo,String codigoVendedor) {
        var key = KEY_CACHE_CARTEIRA.concat(codigo).concat(codigoVendedor);
        return getCarteiraFromCache(key)
                .switchIfEmpty(
                            repository
                                    .findByCodigoAndCodigoVendedorOrderByNomeClienteAsc(codigo,codigoVendedor)
                                    .flatMap(carteira ->   updateCarteiraCache(key,mapper.map(carteira, CarteiraResponse.class)))
                );

    }

    @Override
    public Mono<TotalizadorCarteiraResponse> buscarTotalizador(String codigo,String codigoVendedor) {
        var key = KEY_CACHE_TOTALIZADOR_CARTEIRA.concat(codigo).concat(codigoVendedor);
        return getTotalizadorFromCache(key)
                .switchIfEmpty(
                        totalizadorRepository
                                .totalizarByCodigoAndCodigoVendedor(codigo,codigoVendedor)
                                .flatMap(totalizador ->
                                        updateTotalizadorCache(key
                                                ,mapper.map(totalizador, TotalizadorCarteiraResponse.class)))
                );
    }

    private Flux<CarteiraResponse> getCarteiraFromCache(String key) {

        RMapReactive<Integer,CarteiraResponse> mapReactive = redissonReactiveClient
                .getMap(key, new TypedJsonJacksonCodec(Integer.class,CarteiraResponse.class));
        return mapReactive.readAllValues().flux()
                .filter(carteira -> !carteira.isEmpty())
                .flatMap(carteira -> Flux.fromStream(carteira.stream()))
                .sort(Comparator.comparing(CarteiraResponse::getNomeCliente));
    }


    private Mono<CarteiraResponse> updateCarteiraCache(String key, CarteiraResponse carteira) {
        RMapReactive<Integer,CarteiraResponse> mapReactive = redissonReactiveClient
                .getMap(key, new TypedJsonJacksonCodec(Integer.class,CarteiraResponse.class));
        return mapReactive.fastPut(carteira.getId(),carteira).thenReturn(carteira)
                .doFinally(signalType -> mapReactive.expire(Duration.ofHours(1L)).subscribe());

    }

    private Mono<TotalizadorCarteiraResponse> getTotalizadorFromCache(String key) {
        RMapReactive<Integer,TotalizadorCarteiraResponse> mapReactive = redissonReactiveClient
                .getMap(key, new TypedJsonJacksonCodec(Integer.class,TotalizadorCarteiraResponse.class));
        return mapReactive.get(0);
    }


    private Mono<TotalizadorCarteiraResponse> updateTotalizadorCache(String key, TotalizadorCarteiraResponse totalizador) {
        RMapReactive<Integer,TotalizadorCarteiraResponse> mapReactive = redissonReactiveClient
                .getMap(key, new TypedJsonJacksonCodec(Integer.class,TotalizadorCarteiraResponse.class));
        return mapReactive.fastPut(0,totalizador)
                .thenReturn(totalizador)
                .doFinally(signalType -> mapReactive.expire(Duration.ofHours(1L)).subscribe());
    }


}
