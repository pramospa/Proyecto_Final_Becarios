package com.example.entities;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
    private String degreeFP;
    private String title;
    private LocalDate startDate;
    private LocalDate finishDate;
   
    @Enumerated(EnumType.STRING)
    private EducationCenter educationCenter;

    public enum EducationCenter {
        UNIVERSITY, IES
    }

    private String nameCenter;

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private Becario becario;

}
