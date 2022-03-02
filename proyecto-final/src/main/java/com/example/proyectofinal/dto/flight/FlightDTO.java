package com.example.proyectofinal.dto.flight;

import com.example.proyectofinal.constraint.ChronologicalMatch;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ChronologicalMatch(dateFrom = "goingDate", dateTo = "returnDate",
        message = "La fecha de entrada debe ser menor a la de salida")
public class FlightDTO {
    @NotNull
    private Integer flightNumber;

    @NotEmpty
    private String name;

    @NotEmpty
    private String origin;

    @NotEmpty
    private String destination;

    @NotEmpty
    private String seatType;

    @NotNull
    private Double flightPrice;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate goingDate;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate returnDate;
}
