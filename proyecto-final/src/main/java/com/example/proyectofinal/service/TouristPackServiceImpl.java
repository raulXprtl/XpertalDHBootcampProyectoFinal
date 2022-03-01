package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.CrudResponseDTO;
import com.example.proyectofinal.dto.touristPack.TouristPackRequestDTO;
import com.example.proyectofinal.dto.touristPack.TouristPackResponseDTO;
import com.example.proyectofinal.repository.FlightRepository;
import com.example.proyectofinal.repository.TouristPackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TouristPackServiceImpl implements TouristPackService {
    @Autowired
    private TouristPackRepository touristPackRepository;

    @Override
    public CrudResponseDTO saveTouristPack(TouristPackRequestDTO request) {
        return null;
    }

    @Override
    public CrudResponseDTO updateTouristPack(Long packageNumber, TouristPackRequestDTO request) {
        return null;
    }

    @Override
    public CrudResponseDTO deleteTouristPack(Long packageNumber) {
        return null;
    }

    @Override
    public TouristPackResponseDTO getAllTouristPack() {
        return null;
    }
}
