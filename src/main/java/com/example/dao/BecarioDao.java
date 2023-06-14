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

    @Query(value = "select b from Becario b left join fetch b.becarioInfo left join fetch b.idiomas")
    public List<Becario> findAll(Sort sort);

    @Query(value = "select distinct b.id, b.name,b.surname1,b.center from Becario b")
    public List<Object> findFilteredBecario();
  
    @Query(value = "select distinct b.name,b.surname1,b.surname2,b.id,b.gender,b.center, i.title from Becario b inner join b.becarioInfo i where b.id = :id")
    public List<Object> filterById(int id);
    
    @Query(value = "select distinct b.name,b.surname1,b.surname2,b.id,b.gender,b.center, i.title from Becario b inner join b.becarioInfo i where b.name = :name")
    public List<Object> filterByName (String name);

    @Query(value = "select distinct b.name,b.surname1,b.surname2,b.id,b.gender,b.center, i.title from Becario b inner join b.becarioInfo i where b.surname1 = :surname1")
    public List<Object> filterBySurname1(String surname1);

    @Query(value = "select distinct b.name,b.surname1,b.surname2,b.id,b.gender,b.center, f.comments from Becario b inner join b.feedback f where b.id = :id")
    public List<Object> filterByFeedbackId(int id);




}