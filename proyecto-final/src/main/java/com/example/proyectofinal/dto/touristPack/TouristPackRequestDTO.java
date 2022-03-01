package com.example.proyectofinal.dto.touristPack;

import com.example.proyectofinal.dto.BookResDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class TouristPackRequestDTO {

    private Long packageNumber;
    private String name;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate creation_date;
    private Long client_id;
    private List<BookResDTO> bookingsOrReservations;


}
