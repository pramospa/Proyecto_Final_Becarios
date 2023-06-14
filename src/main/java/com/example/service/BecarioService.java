package com.example.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.example.entities.Becario;

public interface BecarioService {
    
    public Becario findById(int id);   
    public List<Becario> findByName(String name);
    public List<Becario> findBySurname1(String surname1);

    public Becario save(Becario becario);
    public void delete(Becario becario);
    public List<Becario> findAll(Sort sort);
    public List<Object> findFilteredBecario();
    public List<Object> filterById(int id);
    public List<Object> filterByName (String name);
    public List<Object> filterBySurname1(String surname1);
    public List<Object> filterByFeedbackId(int id);

}

