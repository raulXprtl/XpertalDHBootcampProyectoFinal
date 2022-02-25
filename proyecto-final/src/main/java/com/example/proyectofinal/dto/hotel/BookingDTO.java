package com.example.proyectofinal.dto.hotel;

import com.example.proyectofinal.dto.PaymentMethodDTO;
import com.example.proyectofinal.dto.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

    private Long bookingId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String destination;
    private String hotelCode;
    private Integer peopleAmount;
    private String roomType;
    private List<PersonDTO> people;
    private PaymentMethodDTO paymentMethod;

}
