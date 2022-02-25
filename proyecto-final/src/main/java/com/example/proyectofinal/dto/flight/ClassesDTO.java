package com.example.proyectofinal.dto.flight;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassesDTO {
    private String seatType;
    private Integer availableSeats;
    private Float pricePerPerson;
}
