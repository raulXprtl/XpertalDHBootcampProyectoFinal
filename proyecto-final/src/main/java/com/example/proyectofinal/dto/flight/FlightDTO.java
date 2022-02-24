package com.example.proyectofinal.dto.flight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
    private String flightNumber;
    private String origin;
    private String destination;
    private String seatType;
    private Double pricePerPerson;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
