package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.CrudResponseDTO;
import com.example.proyectofinal.dto.flight.*;

import java.util.List;

public interface FlightService {
    List<FlightDTO> getFlights();

    List<FlightDTO> getFlightsAvailable(FlightGetRequestDTO request);

    CrudResponseDTO postFlightReservation(FlightPostRequestDTO request);


    CrudResponseDTO saveNewFlight(FlightNewPostRequestDTO request);

    CrudResponseDTO updateFlight(String flightNumber, FlightNewPostRequestDTO request);

    CrudResponseDTO deleteFlight(String flightNumber);

    CrudResponseDTO updateReservation(Long idReservation, ReservationUpdateDTO request);

    CrudResponseDTO deleteReservation(Long idReservation);

    List<ReservationDTO> getReservations(); // <-- ERROR porque aun no se crea la entidad Reservation
}
