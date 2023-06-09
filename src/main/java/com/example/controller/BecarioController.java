package com.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entities.Becario;
import com.example.service.BecarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/becarios")
@RequiredArgsConstructor

public class BecarioController {

    @Autowired
    private BecarioService becarioService;

    @GetMapping
    public ResponseEntity<List<Becario>> findAll() {

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

    @PostMapping
    public ResponseEntity<Map<String, Object>> saveBecario(@Valid @RequestBody Becario becario, BindingResult results) {

        Map<String, Object> responseAsMap = new HashMap<>();

        ResponseEntity<Map<String, Object>> responseEntity = null;

        if (results.hasErrors()) {

            List<String> mensajesError = new ArrayList<>();
            List<ObjectError> objectErrors = results.getAllErrors();

            for (ObjectError objectError : objectErrors) {
                mensajesError.add(objectError.getDefaultMessage());

            }

            responseAsMap.put("errores: ", mensajesError);
            responseAsMap.put("becario: ", becario);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;

        }

        try {

            Becario becarioPersistido = becarioService.save(becario);

            String successMessage = "El becario se ha creado exitosamente";

            responseAsMap.put("mensaje: ", successMessage);
            responseAsMap.put("becario: ", becarioPersistido);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);

        } catch (DataAccessException e) {

            String errorMessage = "El becario no se pudo persistir y la causa mas probable del error es: " +
                    e.getMostSpecificCause();

            responseAsMap.put("error: ", errorMessage);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return responseEntity;

    }
}
