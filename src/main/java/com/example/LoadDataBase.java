package com.example;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.entities.Becario;
import com.example.entities.BecarioInfo;
import com.example.entities.Becario.Center;
import com.example.entities.Becario.Gender;
import com.example.entities.BecarioInfo.EducationCenter;
import com.example.service.BecarioInfoService;
import com.example.service.BecarioService;

@Configuration
public class LoadDataBase {

    @Bean
    public CommandLineRunner sampleData(BecarioService becarioService, BecarioInfoService becarioInfoService){

        return args -> {

            becarioInfoService.save(BecarioInfo.builder()
                                    .id(1)
                                    .degreeFP("fontanero")
                                    .title("Fontaneria Avanzada")
                                    .startDate(LocalDate.of(2020, 03, 03))
                                    .finishDate(LocalDate.of(2023, 01, 01))
                                    .educationCenter(EducationCenter.IES)
                                    .nameCenter("IES Cervantes")
                                    .build());

            becarioService.save(Becario.builder()
                                .id(1)
                                .name("pepe")
                                .surname1("aaaap")
                                .surname2("bbbbp")
                                .birthday(LocalDate.of(2023, 01, 01))
                                .gender(Gender.MAN)
                                .center(Center.MURCIA)
                                .becarioInfo(becarioInfoService.findById(1))
                                .build());




        };
    }
    
}
