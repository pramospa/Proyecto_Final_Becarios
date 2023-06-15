package com.example.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.entities.Becario;
import com.example.model.FileUploadResponse;
import com.example.service.BecarioService;
import com.example.service.FeedbackService;
import com.example.utilities.FileDownloadUtil;
import com.example.utilities.FileUploadUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/becarios")
@RequiredArgsConstructor
public class BecarioController {
    @Autowired
    private BecarioService becarioService;

    private final FileUploadUtil fileUploadUtil;

    private final FileDownloadUtil fileDownloadUtil;

    @Autowired
    private FeedbackService feedbackService;

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

    @GetMapping("/{id}")

    public ResponseEntity<Map<String, Object>> findByIdBecario(
            @PathVariable(name = "id", required = true) Integer idBecario) {

        ResponseEntity<Map<String, Object>> responseEntity = null;

        Map<String, Object> responseAsMap = new HashMap<>();

        try {

            Becario becario = becarioService.findById(idBecario);

            if (becario != null) {
                String successMessage = "Se ha encontrado el becario con ID: " + idBecario;

                responseAsMap.put("mensaje: ", successMessage);
                responseAsMap.put("becario: ", becario);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);

            } else {

                String notFoundMessage = "No se ha encontrado el becario con el ID: " + idBecario;

                responseAsMap.put("mensaje: ", notFoundMessage);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.NOT_FOUND);

            }

        } catch (DataAccessException e) {

            String errorMessage = "Error grave, y la causa mas probable es: " + e.getMostSpecificCause();

            responseAsMap.put("error grave:", errorMessage);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return responseEntity;

    }

    @PostMapping(consumes = "multipart/form-data")
    @Transactional
    public ResponseEntity<Map<String, Object>> saveBecario(
        @Valid
        @RequestPart(name = "becario") Becario becario, 
        BindingResult results,  
        @RequestPart(name = "file", required = false) MultipartFile file) throws IOException {

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
        if(!file.isEmpty()) {
            String fileCode = fileUploadUtil.saveFile(file.getOriginalFilename(), file);
            becario.setImagenProducto(fileCode+ "-" + file.getOriginalFilename());

            FileUploadResponse fileUploadResponse = FileUploadResponse
                       .builder()
                       .fileName(fileCode + "-" + file.getOriginalFilename())
                       .downloadURI("/productos/downloadFile/" 
                                 + fileCode + "-" + file.getOriginalFilename())
                       .size(file.getSize())
                       .build();
            
            responseAsMap.put("Foto becario: ", fileUploadResponse);           

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

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateBecario(@Valid @RequestBody Becario becario,
            BindingResult results,
            @PathVariable(name = "id") Integer idBecario) {

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

            becario.setId(idBecario);
            Becario becarioActualizado = becarioService.save(becario);
            String successMessage = "El becario se ha actualizado exitosamente";
            responseAsMap.put("mensaje: ", successMessage);
            responseAsMap.put("becario: ", becarioActualizado);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);

        } catch (DataAccessException e) {

            String errorMessage = "El becario no se pudo actualizar y la causa mas probable del error es: " +
                    e.getMostSpecificCause();

            responseAsMap.put("error: ", errorMessage);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return responseEntity;

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteBecario(@PathVariable(name = "id") Integer idBecario) {

        ResponseEntity<Map<String, Object>> responseEntity = null;
        Map<String, Object> responseasMap = new HashMap<>();

        try {

            becarioService.delete(becarioService.findById(idBecario));
            responseasMap.put("mensaje: ", "El becario se ha eliminado correctamente");
            responseEntity = new ResponseEntity<Map<String, Object>>(responseasMap, HttpStatus.OK);

        } catch (DataAccessException e) {

            responseasMap.put("error grave", "No se ha podido eliminar el becario y la causa mas probable es :" +
                    e.getMostSpecificCause());

            responseEntity = new ResponseEntity<Map<String, Object>>(responseasMap, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return responseEntity;

    }

    @GetMapping("/downloadFile/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable(name = "fileCode") String fileCode) {

        Resource resource = null;

        try {
            resource = fileDownloadUtil.getFileAsResource(fileCode);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found ", HttpStatus.NOT_FOUND);
        }

        // 8 byte un 
        String contentType = "application/octet-stream";
        
        //attachment fichero adjunto
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType(contentType))
                            .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                            .body(resource);

    }
}
