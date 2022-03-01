package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.CrudResponseDTO;
import com.example.proyectofinal.dto.hotel.*;

import java.util.List;

public interface HotelService {
    List<HotelDTO> getHotels();

    List<HotelDTO> getHotelsAvailable(HotelGetRequestDTO request);

    CrudResponseDTO saveNewHotel(HotelDTO request);

    CrudResponseDTO updateHotel(Integer hotelCode, HotelDTO request);

    CrudResponseDTO deleteHotel(Integer hotelCode);
}
