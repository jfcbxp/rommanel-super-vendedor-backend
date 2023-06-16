package com.jfcbxp.supervendedor.client;


import com.jfcbxp.supervendedor.config.ForexClientConfig;
import com.jfcbxp.supervendedor.dto.request.AgendamentoRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(name="agendamento-client", url="${clients.base.url}", configuration = ForexClientConfig.class)
public interface AgendamentoClient {
    @PutMapping(value = "/APIZAP/v1/atualizar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<Void> atualizar(@RequestBody AgendamentoRequest agendamento);

    @PostMapping(value = "/APIZAP/v1/cadastrar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<Void> cadastrar(@RequestBody AgendamentoRequest agendamento);

    @DeleteMapping(value = "/APIZAP/v1/deletar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Mono<Void> deletar(@RequestBody AgendamentoRequest agendamento);

}
