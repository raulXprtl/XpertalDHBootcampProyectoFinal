package com.example.proyectofinal.controller;

import com.example.proyectofinal.dto.CrudResponseDTO;
import com.example.proyectofinal.dto.touristPack.TouristPackRequestDTO;
import com.example.proyectofinal.dto.touristPack.TouristPackResponseDTO;
import com.example.proyectofinal.dto.touristPack.TouristPackUpdateDTO;
import com.example.proyectofinal.service.TouristPackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TouristPackController {
    @Autowired
    private TouristPackService touristPack;

    //--------------------------------ALTA DE TOURIST PACK--------------------------------
    @PostMapping(path = "/api/v1/touristicpackage/new")
    public ResponseEntity<CrudResponseDTO> newTouristPack(@RequestBody TouristPackRequestDTO request) {
        return new ResponseEntity<>(touristPack.saveTouristPack(request), HttpStatus.OK);
    }

    //--------------------------------MODIFICACION TOURIST PACK----------------------------
    @PutMapping(path = "/api/v1/touristicpackage/edit")
    public ResponseEntity<CrudResponseDTO> updateTouristPack(@RequestParam Integer packageNumber, @RequestBody TouristPackUpdateDTO request) {
        return new ResponseEntity<>(touristPack.updateTouristPack(packageNumber, request), HttpStatus.OK);
    }

    //--------------------------------ELIMINACION TOURIST PACK------------------------------
    @DeleteMapping(path = "/api/v1/touristicpackage/delete")
    public ResponseEntity<CrudResponseDTO> deleteTouristPack(@RequestParam Integer packageNumber) {
        return new ResponseEntity<>(touristPack.deleteTouristPack(packageNumber), HttpStatus.OK);
    }

    //--------------------------------CONSULTA DE TOURIST PACK--------------------------------
    @GetMapping(path = "/api/v1/touristicpackages")
    public ResponseEntity<TouristPackResponseDTO> getAllTouristPack() {
        return new ResponseEntity<>(touristPack.getAllTouristPack(), HttpStatus.OK);
    }
}

