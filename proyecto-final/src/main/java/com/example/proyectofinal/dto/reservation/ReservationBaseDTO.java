package com.example.proyectofinal.dto.reservation;

import com.example.proyectofinal.constraint.ChronologicalMatch;
import com.example.proyectofinal.dto.PersonDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ChronologicalMatch(dateFrom = "goingDate", dateTo = "returnDate",
        message = "La fecha de ida debe ser menor a la de vuelta")
public class ReservationBaseDTO {

    private Integer reservationId;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate goingDate;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate returnDate;

    @NotBlank(message = "El origen elegido no existe")
    private String origin;

    @NotBlank(message = "El destino elegido no existe")
    private String destination;

    private Integer flightNumber;

    @NotNull
    @DecimalMin(value = "1", message = "Número mínimo de asientos es 1")
    @Digits(integer = 1, fraction = 0,
            message = "La cantidad de personas debe ser un valor numérico entero de 1 dígito")
    private BigDecimal seats;

    private String seatType;
    private Set<PersonDTO> people;
}
