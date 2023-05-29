package com.jfcbxp.supervendedor.service.impl;

import com.jfcbxp.supervendedor.dto.response.FaturamentoProgressResponse;
import com.jfcbxp.supervendedor.dto.response.FaturamentoResponse;
import com.jfcbxp.supervendedor.dto.response.TotalizadorFaturamentoResponse;
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
import java.util.Comparator;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FaturamentoServiceImpl implements FaturamentoService {

    private static final String KEY_CACHE_FATURAMENTO = "faturamento:";
    private static final String KEY_CACHE_FATURAMENTO_PROGRESSO = "faturamento:progresso:";
    private static final String KEY_CACHE_TOTALIZADOR_FATURAMENTO_DIARIO = "faturamento:totalizador:diario:";
    private static final String KEY_CACHE_TOTALIZADOR_FATURAMENTO_MENSAL = "faturamento:totalizador:mensal:";
    private final FaturamentoRepository repository;
    private final RedissonReactiveClient redissonReactiveClient;
    private final ModelMapper mapper;

    @Override
    public Flux<FaturamentoResponse> buscarFaturamento(String codigoVendedor, LocalDate emissao) {
        var key = KEY_CACHE_FATURAMENTO.concat(codigoVendedor).concat(emissao.toString());
        return getFaturamentoFromCache(key)
                .switchIfEmpty(
                            repository
                                    .findByCodigoVendedorAndEmissao(codigoVendedor,emissao)
                                    .flatMap(faturamento ->   updateFaturamentoCache(key,mapper.map(faturamento, FaturamentoResponse.class)))
                );

    }

    @Override
    public Flux<FaturamentoProgressResponse> buscarProgresso(String codigoVendedor) {
        var key = KEY_CACHE_FATURAMENTO_PROGRESSO.concat(codigoVendedor);
      return getFaturamentoProgressoFromCache(key)
                .switchIfEmpty(
                        getFaturamentoProgressoFromSource(key,codigoVendedor).map(
                                progresso -> {
                                    progresso.setSelected(progresso.getPeriodo().isEqual(LocalDate.now()));
                                    return progresso;
                                }
                        )
                );
    }

    @Override
    public Mono<TotalizadorFaturamentoResponse> buscarTotalizadorDiario(String codigoVendedor, LocalDate emissao) {
        var date = Objects.isNull(emissao) ? LocalDate.now() : emissao;
        var key = KEY_CACHE_TOTALIZADOR_FATURAMENTO_DIARIO.concat(codigoVendedor).concat(date.toString());
        return getTotalizadorFromCache(key)
                .switchIfEmpty(
                        repository
                                .totalizarByCodigoVendedorAndEmissao(codigoVendedor,date,date)
                                .flatMap(totalizador ->
                                        updateTotalizadorCache(key
                                                ,mapper.map(totalizador, TotalizadorFaturamentoResponse.class)))
                );
    }

    @Override
    public Mono<TotalizadorFaturamentoResponse> buscarTotalizadorMensal(String codigoVendedor) {
        var year = LocalDate.now().getYear();
        var month = LocalDate.now().getMonth();
        var key = KEY_CACHE_TOTALIZADOR_FATURAMENTO_MENSAL.concat(codigoVendedor);
        return getTotalizadorFromCache(key)
                .switchIfEmpty(
                        repository
                                .totalizarByCodigoVendedorAndEmissao(codigoVendedor,LocalDate.of(year, month, 1),LocalDate.now())
                                .flatMap(totalizador ->
                                        updateTotalizadorCache(key
                                                ,mapper.map(totalizador, TotalizadorFaturamentoResponse.class)))
                );
    }


    private Flux<FaturamentoResponse> getFaturamentoFromCache(String key) {

        RMapReactive<Integer,FaturamentoResponse> mapReactive = redissonReactiveClient
                .getMap(key, new TypedJsonJacksonCodec(Integer.class,FaturamentoResponse.class));
        return mapReactive.readAllValues().flux()
                .filter(faturamento -> !faturamento.isEmpty())
                .flatMap(faturamento -> Flux.fromStream(faturamento.stream()));
    }


    private Mono<FaturamentoResponse> updateFaturamentoCache(String key, FaturamentoResponse faturamento) {
        RMapReactive<Integer,FaturamentoResponse> mapReactive = redissonReactiveClient
                .getMap(key, new TypedJsonJacksonCodec(Integer.class,FaturamentoResponse.class));
        return mapReactive.fastPut(faturamento.getId(),faturamento).thenReturn(faturamento)
                .doFinally(signalType -> mapReactive.expire(Duration.ofHours(1L)).subscribe());

    }

    private Flux<FaturamentoProgressResponse> getFaturamentoProgressoFromSource(String key,String codigoVendedor) {
        var year = LocalDate.now().getYear();
        var month = LocalDate.now().getMonth();
        return  repository
                .contarByCodigoVendedorAndEmissao(codigoVendedor,
                        LocalDate.of(year, month, 1),
                        LocalDate.of(year, month, LocalDate.now().getDayOfMonth()))
                .flatMap(faturamento -> updateFaturamentoProgressoCache(key,faturamento))
                .sort(Comparator.comparing(FaturamentoProgressResponse::getPeriodo));

    }

    private Flux<FaturamentoProgressResponse> getFaturamentoProgressoFromCache(String key) {

        RMapReactive<Integer,FaturamentoProgressResponse> mapReactive = redissonReactiveClient
                .getMap(key, new TypedJsonJacksonCodec(Integer.class,FaturamentoProgressResponse.class));
        return mapReactive.readAllValues().flux()
                .filter(faturamento -> !faturamento.isEmpty())
                .flatMap(faturamento -> Flux.fromStream(faturamento.stream()));
    }


    private Mono<FaturamentoProgressResponse> updateFaturamentoProgressoCache(String key, FaturamentoProgressResponse faturamento) {
        RMapReactive<Integer,FaturamentoProgressResponse> mapReactive = redissonReactiveClient
                .getMap(key, new TypedJsonJacksonCodec(Integer.class,FaturamentoProgressResponse.class));
        return mapReactive.fastPut(faturamento.getPeriodo().getDayOfMonth(),faturamento).thenReturn(faturamento)
                .doFinally(signalType -> mapReactive.expire(Duration.ofHours(1L)).subscribe());

    }

    private Mono<TotalizadorFaturamentoResponse> getTotalizadorFromCache(String key) {
        RMapReactive<Integer,TotalizadorFaturamentoResponse> mapReactive = redissonReactiveClient
                .getMap(key, new TypedJsonJacksonCodec(Integer.class,TotalizadorFaturamentoResponse.class));
        return mapReactive.get(0);
    }


    private Mono<TotalizadorFaturamentoResponse> updateTotalizadorCache(String key, TotalizadorFaturamentoResponse totalizador) {
        RMapReactive<Integer,TotalizadorFaturamentoResponse> mapReactive = redissonReactiveClient
                .getMap(key, new TypedJsonJacksonCodec(Integer.class,TotalizadorFaturamentoResponse.class));
        return mapReactive.fastPut(0,totalizador)
                .thenReturn(totalizador)
                .doFinally(signalType -> mapReactive.expire(Duration.ofHours(1L)).subscribe());
    }

}
