package com.example.proyectofinal.dto.flight;

import com.example.proyectofinal.dto.StatusCodeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightPostResponseDTO {
    private String userName;
    private Double amount;
    private Double interest;
    private Double total;
    private FlightReservationBaseDTO flightReservation;
    private StatusCodeDTO statusCode;

    public void setFlightReservation(FlightReservationRequestDTO flightReservation) {
        this.flightReservation = new FlightReservationBaseDTO(
                flightReservation.getDateFrom(),
                flightReservation.getDateTo(),
                flightReservation.getOrigin(),
                flightReservation.getDestination(),
                flightReservation.getFlightNumber(),
                flightReservation.getSeats(),
                flightReservation.getSeatType(),
                flightReservation.getPeople());
    }
}
