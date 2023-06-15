package com.example.entities;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @NotNull(message = "Le degré d'études ne peut pas être nul")
    @NotBlank(message = "Le diplôme d'études du boursier est requis")
    private String degreeFP;

    @NotNull(message = "Le titre des études ne peut être nul")
    @NotBlank(message = "Le diplôme universitaire est requis")
    private String title;

    @NotNull(message = "La date de début ne peut pas être nulle")
    private LocalDate startDate;

    private LocalDate finishDate;

    @NotNull(message = "Le centre éducatif d'origine ne peut être nul")
    @Enumerated(EnumType.STRING)
    private EducationCenter educationCenter;

    public enum EducationCenter {
        UNIVERSITY, IES
    }

    @NotNull(message = "Le nom du centre éducatif d'origine ne peut pas être nul")
    @NotBlank(message = "Le nom du centre éducatif d'origine est requis")
    private String nameCenter;

    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.PERSIST,
            CascadeType.MERGE })
    // @JoinColumn(name = "becario_id")
    @JsonBackReference
    // @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Becario becario;

}