package com.jfcbxp.supervendedor.service;

import com.jfcbxp.supervendedor.security.UserSecurity;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserSecurity> findByUserName(String username);
}
