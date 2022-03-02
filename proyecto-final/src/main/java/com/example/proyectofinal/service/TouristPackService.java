package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.CrudResponseDTO;
import com.example.proyectofinal.dto.touristPack.TouristPackRequestDTO;
import com.example.proyectofinal.dto.touristPack.TouristPackResponseDTO;
import com.example.proyectofinal.dto.touristPack.TouristPackUpdateDTO;

public interface TouristPackService {

    CrudResponseDTO saveTouristPack(TouristPackRequestDTO request);

    CrudResponseDTO updateTouristPack(Integer packageNumber, TouristPackUpdateDTO request);

    CrudResponseDTO deleteTouristPack(Integer packageNumber);

    TouristPackResponseDTO getAllTouristPack();
}
