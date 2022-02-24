package com.example.proyectofinal.dto.flight;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightGetRequestDTO {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String origin;
    private String destination;
}
