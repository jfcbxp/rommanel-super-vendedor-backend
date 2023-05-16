package com.jfcbxp.supervendedor.resource.impl;

import com.jfcbxp.supervendedor.dto.request.AuthRequest;
import com.jfcbxp.supervendedor.dto.response.AuthResponse;
import com.jfcbxp.supervendedor.resource.LoginResource;
import com.jfcbxp.supervendedor.security.JWTUtil;
import com.jfcbxp.supervendedor.security.PBKDF2Encoder;
import com.jfcbxp.supervendedor.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class LoginResourceImpl implements LoginResource {

    private final JWTUtil jwtUtil;
    private final PBKDF2Encoder passwordEncoder;
    private final UserService userService;
    @Override
    public Mono<ResponseEntity<AuthResponse>> login(AuthRequest authRequest) {
        return userService.findByUserName(authRequest.getUsername())
                .filter(userDetails -> passwordEncoder.encode(authRequest.getPassword()).equals(userDetails.getPassword()))
                .map(userDetails -> ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(userDetails))))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    @Override
    public Mono<ResponseEntity<AuthResponse>> update(AuthResponse authRequest) {
        return Mono.just(ResponseEntity.ok(new AuthResponse(jwtUtil.updateToken(authRequest.getToken()))));
    }
}
