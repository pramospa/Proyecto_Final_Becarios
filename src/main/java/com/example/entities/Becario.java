package com.example.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "becarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Becario implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    
    // @NotNull(message = "El nombre no puede ser nulo")
    // @NotBlank(message = "EL nombre del becario es requerido")
    @Size(min = 1, max = 50, message = "El nombre no puede tener menos de 1 caracteres ni mas de 50")
    private String name;
    
    // @NotNull(message = "El apellido no puede ser nulo")
    // @NotBlank(message = "EL apellido del becario es requerido")
    @Size(min = 1, max = 50, message = "El apellido no puede tener menos de 1 caracteres ni mas de 50")
    private String surname1;
   
    private String surname2;
    
    // @NotNull(message = "La fecha de nacimiento no puede ser nula")
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
  //  @NotNull(message = "El genero no puede ser nulo")
    private Gender gender;
    public enum Gender {
        MAN, WOMAN, NONBINARY, OTHER
    }

    @Enumerated(EnumType.STRING)
    // @NotNull(message = "El centro de procedencia no puede ser nulo")
    private Center center;
    public enum Center {
        MURCIA, VALENCIA
    }
   
    @OneToOne(fetch = FetchType.LAZY) //, cascade = CascadeType.PERSIST, mappedBy = "becario")
    @JsonIgnore
    private BecarioInfo becarioInfo;

    @OneToMany(fetch = FetchType.LAZY) // , cascade = CascadeType.PERSIST, mappedBy = "becario")
    @JsonIgnore
    private List<Idiomas> idiomas;

}
