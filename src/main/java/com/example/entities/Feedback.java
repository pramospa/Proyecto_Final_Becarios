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

    @NotNull(message = "Le nom ne peut pas être nul")
    @NotBlank(message = "Le nom est requis")
    @Size(min = 1, max = 50, message = "Le nom ne peut pas avoir moins de 1 caractères ni plus de 50")
    private String name;

    @NotNull(message = "La date de retour ne peut pas être nulle")
    private LocalDate fechaFeedback;

    @NotNull(message = "HR ne peut pas être nul")
    @NotBlank(message = "RH est nécessaire")
    @Size(min = 1, max = 50, message = "Le nom ne peut pas avoir moins de 1 caractères ni plus de 50")
    private String hrUser;

    @NotNull(message = "Le commentaire ne peut pas être nul")
    @NotBlank(message = "Le commentaire est requis")
    @Size(min = 1, max = 1000, message = "Le commentaire ne peut pas avoir moins de 1 caractères ni plus de 1000")
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.PERSIST,
            CascadeType.MERGE })
    // @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonBackReference
    private Becario becario;

}