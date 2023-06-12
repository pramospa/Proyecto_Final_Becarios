package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.IdiomasDao;
import com.example.entities.Idiomas;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IdiomasServiceImpl implements IdiomasService{

    private final IdiomasDao idiomasDao;

    @Override
    public List<Idiomas> findAll() {
    return idiomasDao.findAll();
    }

    @Override
    @Transactional
    public void save(Idiomas idiomas) {
        idiomasDao.save(idiomas);
    }

    @Override
    public Idiomas findById(int id) {
        return idiomasDao.findById(id).get();
    
    }
    
}
