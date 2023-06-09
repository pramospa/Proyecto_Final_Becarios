package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Becario;
import com.example.service.BecarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/becarios")
@RequiredArgsConstructor


public class BecarioController {

    @Autowired
    private BecarioService becarioService;

    @GetMapping
    public ResponseEntity<List<Becario>> findAll(){

        List<Becario> becarios = new ArrayList<>();
        Sort sortByName = Sort.by("name");

        ResponseEntity<List<Becario>> responseEntity = null;

        try {
            becarios = becarioService.findAll(sortByName);
            responseEntity = new ResponseEntity<List<Becario>>(becarios, HttpStatus.OK);

        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return responseEntity;
    }
    
}
