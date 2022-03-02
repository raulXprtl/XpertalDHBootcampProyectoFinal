package com.example.proyectofinal.dto.reservation;

import com.example.proyectofinal.dto.StatusCodeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationPostResponseDTO {
    private String userName;
    private Double amount;
    private Double interest;
    private Double total;
    private ReservationBaseDTO flightReservation;
    private StatusCodeDTO statusCode;

    public void setFlightReservation(ReservationRequestDTO flightReservation) {
        this.flightReservation = new ReservationBaseDTO(
                flightReservation.getReservationId(),
                flightReservation.getGoingDate(),
                flightReservation.getReturnDate(),
                flightReservation.getOrigin(),
                flightReservation.getDestination(),
                flightReservation.getFlightNumber(),
                flightReservation.getSeats(),
                flightReservation.getSeatType(),
                flightReservation.getPeople());
    }
}
