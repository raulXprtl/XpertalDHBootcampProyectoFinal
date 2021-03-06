package com.example.proyectofinal.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelGetRequestDTO {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String destination;
}
