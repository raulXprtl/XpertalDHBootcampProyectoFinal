package com.example.proyectofinal.dto.flight;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlightNewPostRequestDTO {


    @NotEmpty(message = "El campo 'flightNumber' no puede estar vacío")
    @NotNull(message = "El campo 'flightNumber' no puede ser nulo")
    private String flightNumber;

    private String name;

    @NotEmpty(message = "El campo 'origin' no puede estar vacío")
    @NotNull(message = "El campo 'origin' no puede estar nulo")
    private String origin;

    @NotEmpty(message = "El campo 'destination' no puede estar vacío")
    @NotNull(message = "El campo 'destination' no puede estar nulo")
    private String destination;

    private String seatType;

    private Float flightPrice;

    @NotEmpty(message = "El campo 'dateFrom' no puede estar vacío")
    @NotNull(message = "El campo 'dateFrom' no puede estar nulo")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate goingDate;

    @NotEmpty(message = "El campo 'dateTo' no puede estar vacío")
    @NotNull(message = "El campo 'dateTo' no puede estar nulo")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate returnDate;
}
