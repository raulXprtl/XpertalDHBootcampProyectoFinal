package com.example.proyectofinal.dto.flight;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightPostRequestDTO {
    @NotEmpty
    @Email(message = "Por favor ingrese un e-mail válido",
            regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String userName;
    @Valid
    private FlightReservationRequestDTO flightReservation;
}
