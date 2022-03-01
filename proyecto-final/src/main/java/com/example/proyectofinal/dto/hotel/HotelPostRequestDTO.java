package com.example.proyectofinal.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelPostRequestDTO {

    private String hotelCode;
    private String name;
    private String place;
    private String roomType;
    private Double roomPrice;
    private LocalDate disponibilityDateFrom;
    private LocalDate disponibilityDateTo;
    private Boolean isBooking;
}
