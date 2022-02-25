package com.example.proyectofinal.dto.flight;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationUpdateDTO {

    @NotEmpty
    @Email(message = "Por favor ingrese un e-mail válido",
            regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String userName;

    @Valid
    private EditableReservationDTO flightReservation;
}
