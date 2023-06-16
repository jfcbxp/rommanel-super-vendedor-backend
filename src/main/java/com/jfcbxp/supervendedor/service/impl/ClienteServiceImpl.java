package com.jfcbxp.supervendedor.service.impl;

import com.jfcbxp.supervendedor.dto.response.ClienteResponse;
import com.jfcbxp.supervendedor.repository.ClienteRepository;
import com.jfcbxp.supervendedor.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;
    private final ModelMapper mapper;

    @Override
    public Flux<ClienteResponse> buscarCliente(String pesquisa) {
        return repository.findByPesquisa(pesquisa)
                .map(cliente -> mapper.map(cliente, ClienteResponse.class));
    }

}
