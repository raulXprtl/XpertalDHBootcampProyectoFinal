package com.example.proyectofinal.dto.touristPack;

import com.example.proyectofinal.dto.BookResDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TouristPackUpdateDTO {
    private String name;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate creation_date;
    private Integer client_id;
    private BookResDTO bookingsOrReservations;
}
