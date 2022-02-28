package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.CrudResponseDTO;
import com.example.proyectofinal.dto.touristPack.TouristPackRequestDTO;
import com.example.proyectofinal.dto.touristPack.TouristPackResponseDTO;

public interface TouristPackService {

    CrudResponseDTO saveTouristPack(TouristPackRequestDTO request);

    CrudResponseDTO updateTouristPack(Long packageNumber, TouristPackRequestDTO request);

    CrudResponseDTO deleteTouristPack(Long packageNumber);

    TouristPackResponseDTO getAllTouristPack();
}
