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
import com.example.user.Role;
import com.example.user.User;
import com.example.user.UserServices;


@Configuration
public class LoadDataBase {

    @Bean
    public CommandLineRunner sampleData(BecarioService becarioService, BecarioInfoService becarioInfoService,
                                         IdiomasService idiomasService, FeedbackService feedbackService,
                                         UserServices userServices){

        return args -> {
            becarioService.save(Becario.builder()
                                .id(1)
                                .name("Antonio")
                                .surname1("Palao")
                                .surname2("Vicente")
                                .birthday(LocalDate.of(1980, 01, 01))
                                .gender(Gender.MAN)
                                .center(Center.MURCIA)
                                .imagenProducto("stagiaire.jpeg")
                                .build());

            becarioService.save(Becario.builder()
                                .id(2)
                                .name("Sara")
                                .surname1("Ortuño")
                                .surname2("Baeza")
                                .birthday(LocalDate.of(1969, 9, 01))
                                .gender(Gender.WOMAN)
                                .center(Center.VALENCIA)
                                .imagenProducto("stagiaire.jpeg")
                                .build());

            becarioService.save(Becario.builder()
                                .id(3)
                                .name("Pedro")
                                .surname1("Ramos")
                                .surname2("Chinchilla")
                                .birthday(LocalDate.of(2000, 6, 25))
                                .gender(Gender.OTHER)
                                .center(Center.VALENCIA)
                                .imagenProducto("stagiaire.jpeg")
                                .build());
                            
            becarioInfoService.save(BecarioInfo.builder()
                                 .id(1)
                                 .degreeFP("Fontaneria")
                                 .title("Fontaneria Avanzada")
                                 .startDate(LocalDate.of(2020, 03, 03))
                                 .finishDate(LocalDate.of(2023, 01, 01))
                                 .educationCenter(EducationCenter.IES)
                                 .nameCenter("IES Cervantes")
                                 .becario(becarioService.findById(1))
                                 .build());

            becarioInfoService.save(BecarioInfo.builder()
                                 .id(2)
                                 .degreeFP("Electricidad")
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
                                    .name("Antonio")
                                    .fechaFeedback(LocalDate.of(2023, Month.APRIL, 16))
                                    .hrUser("Irene")
                                    .comments("Écrire de feedback")
                                    .becario(becarioService.findById(2))
                                    .build());  
            feedbackService.saveFeedback(Feedback.builder()
                                    .id(2)
                                    .name("Sara")
                                    .fechaFeedback(LocalDate.of(2023, Month.APRIL, 16))
                                    .hrUser("Irene")
                                    .comments("Écrire de feedback")
                                    .becario(becarioService.findById(2))
                                    .build()); 
            feedbackService.saveFeedback(Feedback.builder()
                                    .id(3)
                                    .name("Pedro")
                                    .fechaFeedback(LocalDate.of(2023, Month.APRIL, 16))
                                    .hrUser("Irene")
                                    .comments("Écrire de feedback")
                                    .becario(becarioService.findById(3))
                                    .build());  
                                               
            userServices.add(User.builder()
                                    .id(1)
                                    .firstName("user")
                                    .lastName("ApellidoUser")
                                    .email("user@email.com")
                                    .password("12345")
                                    .role(Role.USER)
                                    .build());    
                                    
            userServices.add(User.builder()
                                    .id(2)
                                    .firstName("userHR")
                                    .lastName("ApellidoUserHR")
                                    .email("userHR@email.com")
                                    .password("123456")
                                    .role(Role.USER_HR)
                                    .build());      
            userServices.add(User.builder()
                                    .id(3)
                                    .firstName("admin")
                                    .lastName("ApellidoAdmin")
                                    .email("admin@email.com")
                                    .password("admin")
                                    .role(Role.ADMIN)
                                    .build());      

         };
    }
}
