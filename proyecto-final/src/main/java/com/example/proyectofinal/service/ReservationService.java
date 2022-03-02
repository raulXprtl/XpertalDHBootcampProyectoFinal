package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.CrudResponseDTO;
import com.example.proyectofinal.dto.reservation.ReservationPostRequestDTO;
import com.example.proyectofinal.dto.reservation.ReservationRequestDTO;

import java.util.List;

public interface ReservationService {
    CrudResponseDTO postFlightReservation(ReservationPostRequestDTO request);

    CrudResponseDTO updateReservation(Integer idReservation, ReservationPostRequestDTO request);

    CrudResponseDTO deleteReservation(Integer idReservation);

    List<ReservationRequestDTO> getReservations(); // <-- ERROR porque aun no se crea la entidad Reservation
}
