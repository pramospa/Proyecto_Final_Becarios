package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
    Exception {

    // The New Lambda DSL Syntax

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.GET, "/becarios/**", "/filtrobecarios/**").permitAll();
                    auth.requestMatchers("/users/**").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/becarios/**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.PUT, "/becarios/**", "/filtrobecarios/**").permitAll();
                    auth.requestMatchers(HttpMethod.DELETE, "/becarios/**", "/filtrobecarios/**").hasAuthority("ADMIN");
                    auth.anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults()).build();
    }

    

}
