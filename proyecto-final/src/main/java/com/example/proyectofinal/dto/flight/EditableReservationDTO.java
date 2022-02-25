package com.example.proyectofinal.dto.flight;

import com.example.proyectofinal.constraint.ChronologicalMatch;
import com.example.proyectofinal.dto.PaymentMethodDTO;
import com.example.proyectofinal.dto.PersonDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ChronologicalMatch(dateFrom = "dateFrom", dateTo = "dateTo",
        message = "La fecha de ida debe ser menor a la de vuelta")
public class EditableReservationDTO {

    private Long reservationId;

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

    private String flightNumber;

    @NotNull
    @DecimalMin(value = "1", message = "Número mínimo de asientos es 1")
    @Digits(integer = 1, fraction = 0,
            message = "La cantidad de personas debe ser un valor numérico entero de 1 dígito")
    private BigDecimal seats;

    private String seatType;
    private List<PersonDTO> people;

    @Valid
    private PaymentMethodDTO paymentMethodDTO;
}
