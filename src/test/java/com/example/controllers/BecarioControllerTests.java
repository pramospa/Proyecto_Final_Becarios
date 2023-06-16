package com.example.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.dao.BecarioInfoDao;
import com.example.entities.Becario;
import com.example.entities.BecarioInfo;
import com.example.entities.BecarioInfo.EducationCenter;
import com.example.entities.Feedback;
import com.example.entities.Idiomas;
import com.example.entities.Language;
import com.example.entities.Nivel;
import com.example.entities.Becario.Center;
import com.example.entities.Becario.Gender;
import com.example.service.BecarioService;
import com.example.service.BecarioServiceImpl;
import com.example.utilities.FileDownloadUtil;
import com.example.utilities.FileUploadUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

/**
 * @WebMvcTest.
 * 
 * Para probar los controladores, si no tuviesemos configurado Spring Security
 * Permitiria cargar el controlador especifico y sus dependencias sin tener que
 * cargar todo
 * el contexto de la aplicacion, y aqui es donde tendriamos un problema con
 * Spring Security,
 * pues si necesitamos levantar todo el contexto de la aplicacion.
 * Tambien permite autoconfigurar MockMvc para realizar test a los controladores
 */
@Transactional
@SpringBootTest
@AutoConfigureMockMvc // Test a los controladores, a los end points, teniendo Spring Securiy
                      // configurado
// @ContextConfiguration(classes = SecurityConfig.class)
// @WebAppConfiguration
@AutoConfigureTestDatabase(replace = Replace.NONE)
// @WithMockUser(username = "vrmachado@gmail.com",
// authorities = {"ADMIN", "USER"})
// @WithMockUser(roles="ADMIN") - Error 403
public class BecarioControllerTests {

        @Autowired
        private MockMvc mockMvc; // Simular peticiones HTTP

        // Permite agregar objetos simulados al contexto de la aplicacion.
        // El simulacro o simulacion va a remplazar cualquier bean existente
        // en el contexto de la aplicacion.
        @MockBean
        private BecarioServiceImpl becarioService;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private FileUploadUtil fileUploadUtil;

        @MockBean
        private FileDownloadUtil fileDownloadUtil;

        @Autowired
        private WebApplicationContext context;

        @BeforeEach
        public void setUp() {
                mockMvc = MockMvcBuilders
                                .webAppContextSetup(context)
                                .apply(springSecurity())
                                .build();
        }

        @Test
        @DisplayName("Test de intento de guardar un becario sin autorizacion")
        void testGuardarBecario() throws Exception {

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
            
            Becario becario = Becario.builder()
                    .name("Antonio")
                    .surname1("Lopez")
                    .surname2("Palao")
                    .birthday(LocalDate.of(1980,03,19))
                    .gender(Gender.WOMAN)
                    .center(Center.MURCIA)
                    .imagenProducto("becario.jpg")
                    .build();
    
                
                String jsonStringProduct = objectMapper.writeValueAsString(becario);

                MockMultipartFile bytesArrayProduct = new MockMultipartFile("becario",
                                null, "application/json", jsonStringProduct.getBytes());

                // multipart: perfoms a POST request
                mockMvc.perform(multipart("/becarios")
                                .file("file", null)
                                .file(bytesArrayProduct))
                                .andDo(print())
                                .andExpect(status().isUnauthorized());

        }

        @DisplayName("Test guardar un becario con usuario mockeado")
        @Test
        @WithMockUser(username = "paa@gmail.com", authorities = { "ADMIN" }) // puede ser {"ADMIN", "USER"}
        void testGuardarBecarioConUserMocked() throws Exception {
                // given
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
            
                Becario becario = Becario.builder()
                    .name("Antonio")
                    .surname1("Lopez")
                    .surname2("Palao")
                    .birthday(LocalDate.of(1980,03,19))
                    .gender(Gender.WOMAN)
                    .center(Center.MURCIA)
                    .imagenProducto("becario.jpg")
                    .build();

                given(becarioService.save(any(Becario.class)))
                                .willAnswer(invocation -> invocation.getArgument(0));
                // when
                String jsonStringProduct = objectMapper.writeValueAsString(becario);

                MockMultipartFile bytesArrayProduct = new MockMultipartFile("becario",
                                null, "application/json", jsonStringProduct.getBytes());

                ResultActions response = mockMvc.perform(multipart("/becarios")
                                .file("file", null)
                                .file(bytesArrayProduct));
                // then

                response.andDo(print())
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.becario.idiomas", is(becario.getIdiomas())))
                                .andExpect(jsonPath("$.becario.feedback", is(becario.getFeedback())))
                                .andExpect(jsonPath("$.becario.becarioInfo", is(becario.getBecarioInfo())));
        }

        @WithMockUser(username = "paa@gmail.com", authorities = { "ADMIN" }) // puede ser {"ADMIN", "USER"}
        public void testListarProductos() throws Exception {

                // given

                List<Becario> becarios = new ArrayList<>();

                Idiomas idiomas = Idiomas.builder()
                    .language(Language.FRENCH)
                    .nivel(Nivel.A1)
                    .build();
                
                Idiomas idiomas1 = Idiomas.builder()
                    .language(Language.FRENCH)
                    .nivel(Nivel.A1)
                    .build();
        
                Feedback feedback = Feedback.builder()
                    .name("Nombre Becario")
                    .fechaFeedback(LocalDate.of(2023, Month.APRIL, 16))
                    .hrUser("Irene")
                    .comments("Escribe un feedback")
                    .build();
                
                Feedback feedback1 = Feedback.builder()
                    .name("Nombre Becario")
                    .fechaFeedback(LocalDate.of(2023, Month.APRIL, 22))
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
                
                BecarioInfo becarioInfo1 = BecarioInfo.builder()
                    .degreeFP("Electricidad")
                    .title("Electricidad Avanzada")
                    .startDate(LocalDate.of(2023, Month.APRIL, 16))
                    .finishDate(LocalDate.of(2020, 03, 03))
                    .educationCenter(EducationCenter.IES)
                    .nameCenter("IES Esparragal")
                    .build();
            
                Becario becario = Becario.builder()
                    .name("Antonio")
                    .surname1("Lopez")
                    .surname2("Palao")
                    .birthday(LocalDate.of(1980,03,19))
                    .gender(Gender.WOMAN)
                    .center(Center.MURCIA)
                    .imagenProducto("becario.jpg")
                    .build();
                
                 Becario becario1 = Becario.builder()
                    .name("Antonio")
                    .surname1("Lopez")
                    .surname2("Palao")
                    .birthday(LocalDate.of(1980,03,19))
                    .gender(Gender.WOMAN)
                    .center(Center.MURCIA)
                    .imagenProducto("becario.jpg")
                    .build();
                
                becarios.add(becario);
                becarios.add(becario1);

                given(becarioService.findAll(Sort.by("name")))
                                .willReturn(becarios);

                // when

                ResultActions response = mockMvc.perform(get("/becarios"));

                // then

                response.andExpect(status().isOk())
                                .andDo(print())
                                .andExpect(jsonPath("$.size()", is(becarios.size())));

        }

       // Test. Recuperar un producto por el id
        @Test
        @WithMockUser(username = "paa@gmail.com", authorities = { "ADMIN" }) // puede ser {"ADMIN", "USER"}
        public void testRecuperarBecarioPorId() throws Exception {
                // given
                int becarioId = 1;

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
                
                Becario becario = Becario.builder()
                    .name("Antonio")
                    .surname1("Lopez")
                    .surname2("Palao")
                    .birthday(LocalDate.of(1980,03,19))
                    .gender(Gender.WOMAN)
                    .center(Center.MURCIA)
                    .imagenProducto("becario.jpg")
                    .build();
                
                given(becarioService.findById(becarioId)).willReturn(becario);

                // when

                ResultActions response = mockMvc.perform(get("/becarios/{id}", becarioId));

                // then

                response.andExpect(status().isOk())
                                .andDo(print())
                                .andExpect(jsonPath("$.becario.name", is(becario.getName())));
        }

        // Test. Producto no encontrado
        @Test
        @WithMockUser(username = "vrmachado@gmail.com", authorities = { "ADMIN" }) // puede ser {"ADMIN", "USER"}
        public void testBecarioNoEncontrado() throws Exception {
                // given
                int becarioId = 1;

                given(becarioService.findById(becarioId)).willReturn(null);

                // when

                ResultActions response = mockMvc.perform(get("/becarios/{id}", becarioId));

                // then

                response.andExpect(status().isNotFound());

        }

        // Test. Actualizar un becario
        // @Test
        // @WithMockUser(username = "vrmachado@gmail.com", authorities = { "ADMIN" }) // puede ser {"ADMIN", "USER"}
        // public void testActualizarBecario() throws Exception {

        //         // given

        //         int becarioId = 1;

        //         Idiomas idiomas = Idiomas.builder()
        //             .language(Language.FRENCH)
        //             .nivel(Nivel.A1)
        //             .build();
                
        //         Feedback feedback = Feedback.builder()
        //             .name("Nombre Becario")
        //             .fechaFeedback(LocalDate.of(2023, Month.APRIL, 16))
        //             .hrUser("Irene")
        //             .comments("Escribe un feedback")
        //             .build();
                
        //         BecarioInfo becarioInfo = BecarioInfo.builder()
        //             .degreeFP("Fontaneria")
        //             .title("Fontaneria Avanzada")
        //             .startDate(LocalDate.of(2023, Month.APRIL, 16))
        //             .finishDate(LocalDate.of(2020, 03, 03))
        //             .educationCenter(EducationCenter.IES)
        //             .nameCenter("IES Esparragal")
        //             .build();
                
        //         Becario becario = Becario.builder()
        //             .name("Antonio")
        //             .surname1("Lopez")
        //             .surname2("Palao")
        //             .birthday(LocalDate.of(1980,03,19))
        //             .gender(Gender.WOMAN)
        //             .center(Center.MURCIA)
        //             .imagenProducto("becario.jpg")
        //             .build();

        //         given(becarioService.findById(becarioId)).willReturn(becarioGuardado)
        //                         .willReturn(becarioGuardado);
        //         given(becarioService.save(any(Becario.class)))
        //                         .willAnswer(invocation -> invocation.getArgument(0));

        //         // when

        //         // Si todo el producto se recibe en el cuerpo de la peticion procedemos
        //         // de la forma siguiente, de lo contrario, si por una parte va el producto
        //         // y por otra la imagen, hay que proceder de manera diferente (muy similar
        //         // al test de persistir un producto con su imagen)

        //         ResultActions response = mockMvc.perform(put("/becarios/{id}", becarioId)
        //                         .contentType(MediaType.APPLICATION_JSON)
        //                         .content(objectMapper.writeValueAsString(becarioActualizado)));

        //         // then

        //         response.andExpect(status().isOk())
        //                         .andDo(print())
        //                         .andExpect(jsonPath("$.producto.name", is(becarioActualizado.getName())));
        // }

        // Test. Eliminar un producto
        @Test
        @WithMockUser(username = "vrmachado@gmail.com", authorities = { "ADMIN" }) // puede ser {"ADMIN", "USER"}
        public void testEliminarBecario() throws Exception {

                // given

                int becarioId = 1;

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
                
                Becario becario = Becario.builder()
                    .name("Antonio")
                    .surname1("Lopez")
                    .surname2("Palao")
                    .birthday(LocalDate.of(1980,03,19))
                    .gender(Gender.WOMAN)
                    .center(Center.MURCIA)
                    .imagenProducto("becario.jpg")
                    .build();
                given(becarioService.findById(becarioId))
                                .willReturn(becario);

                willDoNothing().given(becarioService).delete(becario);

                // when

                ResultActions response = mockMvc.perform(delete("/becarios/{id}", becarioId));

                // then

                response.andExpect(status().isOk())
                                .andDo(print());

        }

}
