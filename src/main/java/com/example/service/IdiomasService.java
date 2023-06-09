package com.example.service;

import java.util.List;

import com.example.entities.Idiomas;

public interface IdiomasService {

    public List<Idiomas> findAll();
    public void save(Idiomas idiomas);
    public Idiomas findById(int id);

}
