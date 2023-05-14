package com.jfcbxp.supervendedor.repository;

import com.jfcbxp.supervendedor.domain.Usuario;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UsuarioRepository extends ReactiveCrudRepository<Usuario,Integer> {
    Mono<Usuario> findByUsername(String username);
}
