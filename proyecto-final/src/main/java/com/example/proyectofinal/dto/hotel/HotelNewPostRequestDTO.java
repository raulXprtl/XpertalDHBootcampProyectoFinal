package com.example.proyectofinal.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HotelNewPostRequestDTO {

    private String hotelCode;
    private String name;
    private String place;
    private String roomType;
    private LocalDate disponibilityDateFrom;
    private LocalDate disponibilityDateTo;
    private Boolean isBooking;
}
