package com.jfcbxp.supervendedor.service.impl;

import com.jfcbxp.supervendedor.enums.UserSecurityRole;
import com.jfcbxp.supervendedor.repository.UsuarioRepository;
import com.jfcbxp.supervendedor.security.PBKDF2Encoder;
import com.jfcbxp.supervendedor.security.UserSecurity;
import com.jfcbxp.supervendedor.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsuarioRepository repository;
    private final PBKDF2Encoder passwordEncoder;

        @Override
    public Mono<UserSecurity> findByUserName(String username) {

            return repository.findByCodigo(username).map(user ->  UserSecurity.builder()
                    .username(user.getCodigo())
                    .fullName(user.getNomeReduzido())
                    .password(passwordEncoder.encode(user.getSenha()))
                    .enabled(true)
                    .roles(Collections.singletonList(UserSecurityRole.ROLE_USER))
                    .build());
    }
}
