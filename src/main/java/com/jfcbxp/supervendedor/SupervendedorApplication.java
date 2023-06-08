package com.jfcbxp.supervendedor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@EnableReactiveFeignClients(basePackages = "com.jfcbxp.supervendedor.client")
public class SupervendedorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupervendedorApplication.class, args);
	}

}
