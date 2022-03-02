package com.example.proyectofinal.dto.booking;

import com.example.proyectofinal.constraint.ChronologicalMatch;
import com.example.proyectofinal.constraint.RoomTypePeopleMatch;
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
@ChronologicalMatch(dateFrom = "dateFrom", dateTo = "dateTo",
        message = "La fecha de entrada debe ser menor a la de salida")
@RoomTypePeopleMatch(roomType = "roomType", peopleAmount = "peopleAmount",
        message = "El tipo de habitación seleccionada no coincide con " +
                "la cantidad de personas que se alojarán en ella")
public class BookingBaseDTO {

    private Integer bookingId;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateFrom;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateTo;

    @NotBlank(message = "El destino elegido no existe")
    private String destination;

    private Integer hotelCode;

    @NotNull
    @DecimalMin(value = "1", message = "Número mínimo de personas es 1")
    @DecimalMax(value = "6", message = "Número máximo de personas es 6")
    @Digits(integer = 1, fraction = 0,
            message = "La cantidad de personas debe ser un valor numérico entero de 1 dígito")
    private BigDecimal peopleAmount;

    private String roomType;
    private Set<PersonDTO> people;
}
