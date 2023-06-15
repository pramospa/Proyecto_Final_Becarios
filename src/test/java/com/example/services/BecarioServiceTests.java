package com.example.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import com.example.dao.BecarioDao;
import com.example.dao.BecarioInfoDao;
import com.example.dao.FeedbackDao;
import com.example.dao.IdiomasDao;
import com.example.entities.Becario;
import com.example.entities.BecarioInfo;
import com.example.entities.BecarioInfo.EducationCenter;
import com.example.entities.Feedback;
import com.example.entities.Idiomas;
import com.example.entities.Language;
import com.example.entities.Nivel;
import com.example.service.BecarioServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

// Para seguir el enfoque de BDD con Mockito
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BecarioServiceTests {

    @Mock
    private BecarioDao becarioDao;

    @Mock
    private BecarioInfoDao becarioInfoDao;

    @Mock
    private FeedbackDao feedbackDao;

    @Mock
    private IdiomasDao idiomasDao;

    @InjectMocks
    private BecarioServiceImpl becarioService;

    private Becario becario;

    @BeforeEach
    void setUp() {
        Idiomas idiomas = Idiomas.builder()
                    .language(Language.FRENCH)
                    .nivel(Nivel.A1)
                    .build();
        
        Feedback feedback = Feedback.builder()
                    .name("Nombre Becario")
                    .fechaFeedback(LocalDate.of(2023, Month.APRIL, 16))
                    .hrUser("Irene")
                    .comments("Escribe un feedback")
                    .build();
        
        BecarioInfo becarioInfo = BecarioInfo.builder()
                    .degreeFP("Fontaneria")
                    .title("Fontaneria Avanzada")
                    .startDate(LocalDate.of(2023, Month.APRIL, 16))
                    .finishDate(LocalDate.of(2020, 03, 03))
                    .educationCenter(EducationCenter.IES)
                    .nameCenter("IES Esparragal")
                    .build();
    
    }

    @Test
    @DisplayName("Test de servicio para persistir un becario")
    public void testGuardarBecario() {

        // given
        given(becarioDao.save(becario)).willReturn(becario);

        // when
        Becario becarioGuardado = becarioService.save(becario);

        // then
        assertThat(becarioGuardado).isNotNull();
    }

    @DisplayName("Recupera una lista vacia de becarios")
    @Test
    public void testEmptyBecarioList() {

        // given
        given(becarioDao.findAll()).willReturn(Collections.emptyList());

        // when
        List<Becario> becarios = becarioDao.findAll();

        // then
        assertThat(becarios).isEmpty();
    }
}