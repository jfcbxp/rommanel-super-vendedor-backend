package com.jfcbxp.supervendedor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SupervendedorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SupervendedorApplication.class, args);
	}

}
