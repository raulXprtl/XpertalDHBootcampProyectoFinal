package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.hotel.HotelDTO;
import com.example.proyectofinal.dto.hotel.HotelGetRequestDTO;
import com.example.proyectofinal.dto.hotel.HotelPostRequestDTO;
import com.example.proyectofinal.dto.hotel.HotelPostResponseDTO;

import java.util.List;

public interface HotelService {
    List<HotelDTO> getHotels();

    List<HotelDTO> getHotelsAvailable(HotelGetRequestDTO request);

    HotelPostResponseDTO postBooking(HotelPostRequestDTO request);
}
