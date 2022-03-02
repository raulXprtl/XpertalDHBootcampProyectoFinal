package com.example.proyectofinal.dto.touristPack;

import com.example.proyectofinal.dto.BookResDTO;
import com.example.proyectofinal.entity.TouristPack;
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
public class TouristPackResponseDTO {
    List<TouristPackDTO> packages;
}
