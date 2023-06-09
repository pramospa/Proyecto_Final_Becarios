package com.example.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entities.Becario;



public interface BecarioDao extends JpaRepository<Becario, Integer> {

    @Query(value = "select b from Becario b left join fetch b.becarioInfo where b.id = :id")
    public Becario findById(int id);

    @Query(value = "select b from Becario b left join fetch b.becarioInfo where b.name = :name")
    public List<Becario> findByName(String name);

    @Query(value = "select b from Becario b left join fetch b.becarioInfo where b.surname1 = :surname1")
    public List<Becario> findBySurname1(String surname1);

    @Query(value = "select b from Becario b left join fetch b.becarioInfo")
    public List<Becario> findAll(Sort sort);


    
}
