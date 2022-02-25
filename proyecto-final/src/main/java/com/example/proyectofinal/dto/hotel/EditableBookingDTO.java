package com.example.proyectofinal.dto.hotel;

import com.example.proyectofinal.dto.PaymentMethodDTO;
import com.example.proyectofinal.dto.PersonDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditableBookingDTO {

    private long bookingId;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateFrom;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateTo;

    @NotBlank(message = "El destino elegido no existe")
    private String destination;

    private String hotelCode;

    @NotNull
    @DecimalMin(value = "1", message = "Número mínimo de personas es 1")
    @DecimalMax(value = "6", message = "Número máximo de personas es 6")
    @Digits(integer = 1, fraction = 0,
            message = "La cantidad de personas debe ser un valor numérico entero de 1 dígito")
    private BigDecimal peopleAmount;

    private String roomType;
    private List<PersonDTO> people;

    @Valid
    private PaymentMethodDTO paymentMethod;
}
