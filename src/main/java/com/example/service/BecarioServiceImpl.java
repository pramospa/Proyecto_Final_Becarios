package com.example.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.dao.BecarioDao;
import com.example.entities.Becario;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BecarioServiceImpl implements BecarioService {

    private final BecarioDao becarioDao;

    @Override
    public Becario findById(int id) {
        return becarioDao.findById(id);
        
    }

    @Override
    public List<Becario> findByName(String name) {
        
        return becarioDao.findByName(name);
    }

    @Override
    public List<Becario> findBySurname1(String surname1) {
        return becarioDao.findBySurname1(surname1);
       
    }

    @Override
    public Becario save(Becario becario) {
        return becarioDao.save(becario);
        
    }

    @Override
    public void delete(Becario becario) {
        becarioDao.delete(becario);
        
    }

    @Override
    public List<Becario> findAll(Sort sort) {
        return becarioDao.findAll(sort);
        
    }
    
}
