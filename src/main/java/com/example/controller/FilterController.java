package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.BecarioService;

@RestController
@RequestMapping("/filtrobecarios")

public class FilterController {

    @Autowired
    private BecarioService becarioService;

    @GetMapping
    public ResponseEntity<List<Object>> findFilteredBecario() {

        List<Object> camposfiltrados = new ArrayList<>();

        ResponseEntity<List<Object>> responseEntity = null;

        try {
            camposfiltrados = becarioService.findFilteredBecario();
            responseEntity = new ResponseEntity<List<Object>>(camposfiltrados, HttpStatus.OK);

        } catch (DataAccessException e) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @GetMapping("/{id}")

    public ResponseEntity<List<Object>> filterByIdBecario(
            @PathVariable(name = "id", required = true) Integer idBecario) {

        List<Object> camposfiltrados = new ArrayList<>();
        ResponseEntity<List<Object>> responseEntity = null;

        try {

            camposfiltrados = becarioService.filterById(idBecario);
            responseEntity = new ResponseEntity<List<Object>>(camposfiltrados, HttpStatus.OK);

        } catch (DataAccessException e) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return responseEntity;

    }


    @GetMapping("/name/{name}")
    public ResponseEntity<List<Object>> filterByNameBecario(
            @PathVariable(name = "name", required = true) String name) {

        List<Object> camposfiltrados = new ArrayList<>();
        ResponseEntity<List<Object>> responseEntity = null;

        try {

            camposfiltrados = becarioService.filterByName(name);
            responseEntity = new ResponseEntity<List<Object>>(camposfiltrados, HttpStatus.OK);

        } catch (DataAccessException e) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return responseEntity;

    }

    @GetMapping("/surname1/{surname1}")

    public ResponseEntity<List<Object>> filterBySurname1Becario(
            @PathVariable(name = "surname1", required = true) String surname1) {

        List<Object> camposfiltrados = new ArrayList<>();
        ResponseEntity<List<Object>> responseEntity = null;

        try {

            camposfiltrados = becarioService.filterBySurname1(surname1);
            responseEntity = new ResponseEntity<List<Object>>(camposfiltrados, HttpStatus.OK);

        } catch (DataAccessException e) {
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return responseEntity;

    }
}