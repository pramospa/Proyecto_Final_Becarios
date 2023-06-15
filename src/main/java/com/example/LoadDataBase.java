package com.example;

import java.time.LocalDate;
import java.time.Month;

import org.springframework.boot.CommandLineRunner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.entities.Becario;
import com.example.entities.BecarioInfo;
import com.example.entities.Feedback;
import com.example.entities.Idiomas;
import com.example.entities.Language;
import com.example.entities.Nivel;
import com.example.entities.Becario.Center;
import com.example.entities.Becario.Gender;
import com.example.entities.BecarioInfo.EducationCenter;
import com.example.service.BecarioInfoService;
import com.example.service.BecarioService;
import com.example.service.FeedbackService;
import com.example.service.IdiomasService;


@Configuration
public class LoadDataBase {

    @Bean
    public CommandLineRunner sampleData(BecarioService becarioService, BecarioInfoService becarioInfoService,
                                         IdiomasService idiomasService, FeedbackService feedbackService){

        return args -> {
            becarioService.save(Becario.builder()
                                .id(1)
                                .name("pepe")
                                .surname1("aaaap")
                                .surname2("bbbbp")
                                .birthday(LocalDate.of(2023, 01, 01))
                                .gender(Gender.MAN)
                                .center(Center.MURCIA)
                                .imagenProducto("test.jpeg")
                                .build());

            becarioService.save(Becario.builder()
                                .id(2)
                                .name("pepa")
                                .surname1("aap")
                                .surname2("bbp")
                                .birthday(LocalDate.of(2023, 01, 01))
                                .gender(Gender.WOMAN)
                                .center(Center.VALENCIA)
                                .imagenProducto("test.jpeg")
                                .build());

            becarioService.save(Becario.builder()
                                .id(3)
                                .name("pepo")
                                .surname1("aap")
                                .surname2("bbp")
                                .birthday(LocalDate.of(2023, 01, 01))
                                .gender(Gender.OTHER)
                                .center(Center.VALENCIA)
                                .imagenProducto("test.jpeg")
                                .build());
                            
            becarioInfoService.save(BecarioInfo.builder()
                                 .id(1)
                                 .degreeFP("fontanero")
                                 .title("Fontaneria Avanzada")
                                 .startDate(LocalDate.of(2020, 03, 03))
                                 .finishDate(LocalDate.of(2023, 01, 01))
                                 .educationCenter(EducationCenter.IES)
                                 .nameCenter("IES Cervantes")
                                 .becario(becarioService.findById(1))
                                 .build());

            becarioInfoService.save(BecarioInfo.builder()
                                 .id(2)
                                 .degreeFP("Electricista")
                                 .title("Electricidad Basica")
                                 .startDate(LocalDate.of(2020, 03, 03))
                                 .finishDate(LocalDate.of(2023, 01, 01))
                                 .educationCenter(EducationCenter.IES)
                                 .nameCenter("IES Esparragal")
                                 .becario(becarioService.findById(2))
                                 .build());

            becarioInfoService.save(BecarioInfo.builder()
                                 .id(3)
                                 .degreeFP("Electromecanica")
                                 .title("Electromecanica Avanzada")
                                 .startDate(LocalDate.of(2020, 03, 03))
                                 .finishDate(LocalDate.of(2023, 01, 01))
                                 .educationCenter(EducationCenter.UNIVERSITY)
                                 .nameCenter("Universidad de Murcia")
                                 .becario(becarioService.findById(3))
                                 .build());  

           
                                 
            

            idiomasService.save(Idiomas.builder()
                                .id(1)
                                .language(Language.ENGLISH)
                                .nivel(Nivel.B1)
                                .becario(becarioService.findById(1))
                                .build());

            idiomasService.save(Idiomas.builder()
                                .id(2)
                                .language(Language.FRENCH)
                                .nivel(Nivel.B2)
                                .becario(becarioService.findById(1))
                                .build());  

            idiomasService.save(Idiomas.builder()
                                .id(3)
                                .language(Language.SPANISH)
                                .nivel(Nivel.B2)
                                .becario(becarioService.findById(2))
                                .build());  

            idiomasService.save(Idiomas.builder()
                                .id(4)
                                .language(Language.ENGLISH)
                                .nivel(Nivel.A1)
                                .becario(becarioService.findById(3))
                                .build()); 
            
            feedbackService.saveFeedback(Feedback.builder()
                                    .id(1)
                                    .name("pepa")
                                    .fechaFeedback(LocalDate.of(2023, Month.APRIL, 16))
                                    .hrUser("Manolita")
                                    .comments("Ha trabajado muy bien, un fiera")
                                    .becario(becarioService.findById(2))
                                    .build());  
            feedbackService.saveFeedback(Feedback.builder()
                                    .id(2)
                                    .name("pepa")
                                    .fechaFeedback(LocalDate.of(2023, Month.APRIL, 16))
                                    .hrUser("Manolita")
                                    .comments("Ha trabajado muy bien, un fiera")
                                    .becario(becarioService.findById(2))
                                    .build()); 
            feedbackService.saveFeedback(Feedback.builder()
                                    .id(3)
                                    .name("pepo")
                                    .fechaFeedback(LocalDate.of(2023, Month.APRIL, 16))
                                    .hrUser("Manolita")
                                    .comments("Ha trabajado muy bien, un fiera")
                                    .becario(becarioService.findById(3))
                                    .build());                     

                                
         };
    }
}
