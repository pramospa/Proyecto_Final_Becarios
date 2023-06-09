package com.example.service;

import org.springframework.stereotype.Service;

import com.example.dao.BecarioInfoDao;
import com.example.entities.Becario;
import com.example.entities.BecarioInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BecarioInfoServiceImpl implements BecarioInfoService {

    private final BecarioInfoDao becarioInfoDao;
   
    @Override
    public void save(BecarioInfo becarioInfo) {
        becarioInfoDao.save(becarioInfo);
        
    }

    @Override
    public BecarioInfo findById(int id) {
        return becarioInfoDao.findById(id).get();
        
    }
    
}
