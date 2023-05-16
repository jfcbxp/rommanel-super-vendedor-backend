package com.jfcbxp.supervendedor.resource;

import com.jfcbxp.supervendedor.dto.request.AuthRequest;
import com.jfcbxp.supervendedor.dto.response.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

public interface LoginResource {

    @PostMapping(value = "/login")
    Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest authRequest);

    @PostMapping(value = "/update")
    Mono<ResponseEntity<AuthResponse>> update(@RequestBody AuthResponse token);

}
