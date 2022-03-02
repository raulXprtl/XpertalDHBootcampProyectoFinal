package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.CrudResponseDTO;
import com.example.proyectofinal.dto.flight.*;

import java.util.List;

public interface FlightService {
    List<FlightDTO> getFlights();

    List<FlightDTO> getFlightsAvailable(FlightGetRequestDTO request);

    CrudResponseDTO saveNewFlight(FlightDTO request);

    CrudResponseDTO updateFlight(Integer flightNumber, FlightDTO request);

    CrudResponseDTO deleteFlight(Integer flightNumber);
}
