package com.example.entities;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
@Table(name = "feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback implements Serializable {

private static final long serialVersionUID = 1L;

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;

@NotNull(message = "El nombre no puede ser nulo")
@NotBlank(message = "EL nombre es requerido")
@Size(min = 1, max = 50, message = "El nombre no puede tener menos de 1 caracteres ni mas de 50")
private String name;

@NotNull(message = "La fecha del feedback no puede ser nula")
private LocalDate fechaFeedback;

@NotNull(message = "El HR no puede ser nulo")
@NotBlank(message = "EL HR es requerido")
@Size(min = 1, max = 50, message = "El nombre no puede tener menos de 1 caracteres ni mas de 50")
private String hrUser;

@NotNull(message = "El comentario no puede ser nulo")
@NotBlank(message = "EL comentario es requerido")
@Size(min = 1, max = 1000, message = "El comentario no puede tener menos de 1 caracteres ni mas de 1000")
private String comments;

@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH,CascadeType.REMOVE,CascadeType.PERSIST,  CascadeType.MERGE})
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonBackReference
private Becario becario;

}
