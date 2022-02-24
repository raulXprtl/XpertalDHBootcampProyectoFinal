package com.example.proyectofinal.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {
    private String hotelId;
    private String name;
    private String location;
    private String roomType;
    private Integer pricePerNight;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Boolean reserved;
}
