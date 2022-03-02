package com.example.proyectofinal.dto.hotel;

import com.example.proyectofinal.constraint.ChronologicalMatch;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ChronologicalMatch(dateFrom = "disponibilityDateFrom", dateTo = "disponibilityDateTo",
        message = "La fecha de entrada debe ser menor a la de salida")
public class HotelDTO {
    @NotNull
    private Integer hotelCode;

    @NotEmpty
    private String name;

    @NotBlank(message = "El destino elegido no existe")
    private String place;

    @NotEmpty
    private String roomType;

    @NotNull
    private Double roomPrice;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate disponibilityDateFrom;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate disponibilityDateTo;

    @NotNull
    private Boolean isBooking;
}
