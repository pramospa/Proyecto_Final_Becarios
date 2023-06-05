package com.example.entities;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String name;
    private String surname1;
    private String surname2;
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public enum Gender {
        MAN, WOMAN, NONBINARY, OTHER
    }

    @Enumerated(EnumType.STRING)
    private Center center;
    public enum Center {
        MURCIA, VALENCIA
    }

    private BecarioInfo becarioInfo;

}
