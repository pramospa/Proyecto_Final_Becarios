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
                                .name("David")
                                .surname1("Fernandez")
                                .surname2("Gomez")
                                .birthday(LocalDate.of(1982, 01, 01))
                                .gender(Gender.MAN)
                                .center(Center.MURCIA)
                                .imagenProducto("test.jpeg")
                                .build());

            becarioService.save(Becario.builder()
                                .id(2)
                                .name("Victor")
                                .surname1("Diaz")
                                .surname2("Navarro")
                                .birthday(LocalDate.of(1975, 01, 01))
                                .gender(Gender.MAN)
                                .center(Center.VALENCIA)
                                .imagenProducto("test.jpeg")
                                .build());

            becarioService.save(Becario.builder()
                                .id(3)
                                .name("Ana")
                                .surname1("Lopez")
                                .surname2("Martinez")
                                .birthday(LocalDate.of(1985, 01, 01))
                                .gender(Gender.WOMAN)
                                .center(Center.VALENCIA)
                                .imagenProducto("test.jpeg")
                                .build());
                            
            becarioInfoService.save(BecarioInfo.builder()
                                 .id(1)
                                 .degreeFP("Plombier")
                                 .title("Plomberie avancee")
                                 .startDate(LocalDate.of(2020, 03, 03))
                                 .finishDate(LocalDate.of(2023, 05, 05))
                                 .educationCenter(EducationCenter.IES)
                                 .nameCenter("IES Cervantes")
                                 .becario(becarioService.findById(1))
                                 .build());

            becarioInfoService.save(BecarioInfo.builder()
                                 .id(2)
                                 .degreeFP("Electricien")
                                 .title("Electricite de base")
                                 .startDate(LocalDate.of(2020, 03, 03))
                                 .finishDate(LocalDate.of(2023, 01, 01))
                                 .educationCenter(EducationCenter.IES)
                                 .nameCenter("IES Esparragal")
                                 .becario(becarioService.findById(2))
                                 .build());

            becarioInfoService.save(BecarioInfo.builder()
                                 .id(3)
                                 .degreeFP("Electromecanique")
                                 .title("Electromecanique avancee")
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
                                    .name("David")
                                    .fechaFeedback(LocalDate.of(2023, Month.APRIL, 16))
                                    .hrUser("Yolanda")
                                    .comments("Il a tres bien travaille")
                                    .becario(becarioService.findById(1))
                                    .build());  
            feedbackService.saveFeedback(Feedback.builder()
                                    .id(2)
                                    .name("Victor")
                                    .fechaFeedback(LocalDate.of(2023, Month.APRIL, 16))
                                    .hrUser("Yolanda")
                                    .comments("Il a atteint les objectifs fix\u00E9s")
                                    .becario(becarioService.findById(2))
                                    .build()); 
            feedbackService.saveFeedback(Feedback.builder()
                                    .id(3)
                                    .name("Ana")
                                    .fechaFeedback(LocalDate.of(2023, Month.APRIL, 16))
                                    .hrUser("Yolanda")
                                    .comments("Elle a tres bien travaille")
                                    .becario(becarioService.findById(3))
                                    .build());  
                                    
            
           
            userServices.add(User.builder()
                                    .id(1)
                                    .firstName("paaa")
                                    .lastName("aaap")
                                    .email("admin@gmail.com")
                                    .password("Temp2023$$")
                                    .role(Role.ADMIN)
                                    .build());    
                                    
            userServices.add(User.builder()
                                    .id(2)
                                    .firstName("peee")
                                    .lastName("aaap")
                                    .email("HR_User1@gmail.com")
                                    .password("Temp2023$$")
                                    .role(Role.USER_HR)
                                    .build());      
            userServices.add(User.builder()
                                    .id(3)
                                    .firstName("piii")
                                    .lastName("aaap")
                                    .email("HR_User2@gmail.com")
                                    .password("Temp2023$$")
                                    .role(Role.USER_HR)
                                    .build());      

         };
    }
}
