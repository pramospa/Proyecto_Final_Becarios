package com.example.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "idiomas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Idiomas implements Serializable {
    
    private static final long serialVersionUID = 1L;

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;

 

@NotNull(message = "El idioma no puede ser nulo")
@NotBlank(message = "El idioma es requerido")
@Size(min = 1, max= 30, message = "El idioma no puede tener menos de 1 caracter ni mas de 30")
private String idioma;

// @NotNull(message = "El nivel de idioma no puede ser nulo")
// @NotBlank(message = "El nivel de idioma es requerido")
@Enumerated(EnumType.STRING)
private Nivel nivel;


@ManyToOne(fetch = FetchType.LAZY)
// @NotNull(message = "No puede existir idioma sin becario")
private Becario becario;

}
