package com.example.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Le nom ne peut pas être nul")
    @NotBlank(message = "Le nom du boursier est requis")
    @Size(min = 1, max = 50, message = "Le nom ne peut pas avoir moins de 1 caractères ni plus de 50")
    private String name;

    @NotNull(message = "Le nom de famille ne peut paas être nul")
    @NotBlank(message = "Le nom de famille du boursier est requis")
    @Size(min = 1, max = 50, message = "Le nom de famille ne peut pas avoir moins de 1 caractères ni plus de 50")
    private String surname1;

    private String surname2;

    @NotNull(message = "La date de naissance ne peut pas être nulle")
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le sexe ne peut pas être nul")
    private Gender gender;

    public enum Gender {
        MAN, WOMAN, NONBINARY, OTHER
    }

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Le centre d'origine ne peut pas être nul")
    private Center center;

    public enum Center {
        MURCIA, VALENCIA
    }

    @NotNull(message = "La photo du produit est requise")
    private String imagenProducto;

    @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.PERSIST,
            CascadeType.MERGE }, mappedBy = "becario")
    @Valid
    // @JsonIgnore
    @JsonManagedReference
    private BecarioInfo becarioInfo;

    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.PERSIST,
            CascadeType.MERGE }, mappedBy = "becario")
    @Valid
    @JsonManagedReference
    // @JsonIgnore
    private List<Idiomas> idiomas;

    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.PERSIST,
            CascadeType.MERGE }, mappedBy = "becario")
    @Valid
    @JsonManagedReference
    // @JsonIgnore
    private List<Feedback> feedback;

}
