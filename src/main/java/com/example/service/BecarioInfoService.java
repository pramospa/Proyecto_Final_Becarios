package com.example.service;


import com.example.entities.BecarioInfo;


public interface BecarioInfoService {
   
    public void save(BecarioInfo becarioInfo);
    public BecarioInfo findById(int id);
   
}
