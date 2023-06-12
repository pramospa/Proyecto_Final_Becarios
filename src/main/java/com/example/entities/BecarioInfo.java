package com.example.entities;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "becariosInfo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class BecarioInfo implements Serializable {

    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    

    @NotNull(message = "El grado de estudios no puede ser nulo")
    @NotBlank(message = "El grado de estudios del becario es requerido")
    private String degreeFP;
    

    @NotNull(message = "El titulo de estudios no puede ser nulo")
    @NotBlank(message = "El titulo de estudios del becario es requerido")
    private String title;
   
    @NotNull(message = "La fecha de inicio no puede ser nula")
    private LocalDate startDate;
   
    private LocalDate finishDate;
   
    @NotNull(message = "El centro educativo de procedencia no puede ser nulo")
    @Enumerated(EnumType.STRING)
    private EducationCenter educationCenter;
    public enum EducationCenter {
        UNIVERSITY, IES
    }

    @NotNull(message = "El nombre del centro educativo de procedencia no puede ser nulo")
    @NotBlank(message = "El nombre del centro educativo de procedencia es requerido")
    private String nameCenter;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Becario becario;

}
 