package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.flight.FlightDTO;
import com.example.proyectofinal.dto.flight.FlightGetRequestDTO;
import com.example.proyectofinal.dto.flight.FlightPostRequestDTO;
import com.example.proyectofinal.dto.flight.FlightPostResponseDTO;

import java.util.List;

public interface FlightService {
    List<FlightDTO> getFlights();

    List<FlightDTO> getFlightsAvailable(FlightGetRequestDTO request);

    FlightPostResponseDTO postFlightReservation(FlightPostRequestDTO request);
}
