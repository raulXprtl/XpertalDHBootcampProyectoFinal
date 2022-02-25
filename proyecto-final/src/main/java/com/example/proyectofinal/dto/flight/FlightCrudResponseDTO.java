package com.example.proyectofinal.dto.flight;

import com.example.proyectofinal.dto.StatusCodeDTO;
import com.example.proyectofinal.entity.Flight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightCrudResponseDTO {

    private String message;
}
