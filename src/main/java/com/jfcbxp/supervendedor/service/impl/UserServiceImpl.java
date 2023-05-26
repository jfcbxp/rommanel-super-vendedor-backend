package com.jfcbxp.supervendedor.service.impl;

import com.jfcbxp.supervendedor.enums.UserSecurityRole;
import com.jfcbxp.supervendedor.repository.UsuarioRepository;
import com.jfcbxp.supervendedor.security.PBKDF2Encoder;
import com.jfcbxp.supervendedor.security.UserSecurity;
import com.jfcbxp.supervendedor.service.UserService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String KEY_CACHE_USER = "user:";
    private final UsuarioRepository repository;
    private final PBKDF2Encoder passwordEncoder;
    private final RedissonReactiveClient redissonReactiveClient;

        @Override
    public Mono<UserSecurity> findByUserName(String username) {
            var key = KEY_CACHE_USER.concat(username);
            return getUserFromCache(key)
                    .switchIfEmpty(
                            repository.findByCodigo(username).map(user ->  UserSecurity.builder()
                                            .username(user.getCodigo())
                                            .fullName(user.getNomeReduzido())
                                            .password(passwordEncoder.encode(user.getSenha()))
                                            .enabled(true)
                                            .roles(Collections.singletonList(UserSecurityRole.ROLE_USER))
                                            .build())
                                    .flatMap(user ->
                                            updateUserCache(key,user))
                    );
    }

    private Mono<UserSecurity> getUserFromCache(String key) {
        RMapReactive<Integer,UserSecurity> mapReactive = redissonReactiveClient
                .getMap(key, new TypedJsonJacksonCodec(Integer.class,UserSecurity.class));
        return mapReactive.get(0);
    }


    private Mono<UserSecurity> updateUserCache(String key, UserSecurity userSecurity) {
        RMapReactive<Integer,UserSecurity> mapReactive = redissonReactiveClient
                .getMap(key, new TypedJsonJacksonCodec(Integer.class,UserSecurity.class));
        return mapReactive.fastPut(0,userSecurity)
                .thenReturn(userSecurity)
                .doFinally(signalType -> mapReactive.expire(Duration.ofHours(1L)).subscribe());
    }
}
