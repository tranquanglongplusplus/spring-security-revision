package com.spring.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class C0501SecurityAuthenticationProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(C0501SecurityAuthenticationProviderApplication.class, args);
    }

}
