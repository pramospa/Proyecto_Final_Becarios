package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.entities.Becario;
import com.example.service.BecarioInfoService;
import com.example.service.BecarioService;

@Configuration
public class LoadDataBase {

    @Bean
    public CommandLineRunner sampleData(BecarioService becarioService, BecarioInfoService becarioInfoService){

        return args -> {

            becarioService.save(Becario.builder().id(1).build());











        }
    }
    
}
