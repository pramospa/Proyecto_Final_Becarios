package com.example.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.user.Role;
import com.example.user.User;
import com.example.user.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    private User userHR;

    @BeforeEach
    void setUp() {
        userHR = User.builder()
                .firstName("UserHR")
                .lastName("Recursos Humanos")
                .password("123456")
                .email("userHR0@email.com")
                .role(Role.USER)
                .build();
    }

    // Test para agregar un user
    @Test
    @DisplayName("Add user")
    public void testAddUser() {

        // given

        User user1 = User.builder()
                .firstName("Test User 1")
                .lastName("Apellidos1")
                .password("123456")
                .email("user1@email.com")
                .role(Role.USER)
                .build();

        // when

        User userAdded = userRepository.save(user1);

        // then

        assertThat(userAdded).isNotNull();
        assertThat(userAdded.getId()).isGreaterThan(0L);

    }

    @DisplayName("Test para listar usuarios")
    @Test
    public void testFindAllUsers() {

        // given
        User user1 = User.builder()
                .firstName("Test User 1")
                .lastName("Apellido1")
                .password("123456")
                .email("user1@email.com")
                .role(Role.USER)
                .build();

        userRepository.save(userHR);
        userRepository.save(user1);

        // Dado los empleados guardados
        // when
        List<User> usuarios = userRepository.findAll();

        // then
        assertThat(usuarios).isNotNull();
        assertThat(usuarios.size()).isEqualTo(2);
    }
    
    @Test
    @DisplayName("Test para recuperar un user por su ID")
    public void findUserById() {

        // given

        userRepository.save(userHR);

        // when

        User user = userRepository.findById(userHR.getId()).get();

        // then

        assertThat(user.getId()).isNotEqualTo(0L);

    }

    @Test
    @DisplayName("Test para actualizar un user")
    public void testUpdateUser() {

        // given

        userRepository.save(userHR);

        // when

        User userGuardado = userRepository.findByEmail(userHR.getEmail()).get();

        userGuardado.setLastName("Irene");
        userGuardado.setFirstName("Lopez");
        userGuardado.setEmail("irene.lopez@email.com");

        User userUpdated = userRepository.save(userGuardado);

        // then

        assertThat(userUpdated.getEmail()).isEqualTo("irene.lopez@email.com");
        assertThat(userUpdated.getFirstName()).isEqualTo("Lopez");

    }

    @DisplayName("Test para eliminar un user")
    @Test
    public void testDeleteUser() {

        // given
        userRepository.save(userHR);

        // when
        userRepository.delete(userHR);
        Optional<User> optionalUser = userRepository.findByEmail(userHR.getEmail());

        // then
        assertThat(optionalUser).isEmpty();
    }
}


