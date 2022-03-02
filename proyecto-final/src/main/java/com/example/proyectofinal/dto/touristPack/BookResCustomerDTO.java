package com.example.proyectofinal.dto.touristPack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResCustomerDTO {
    Integer id;
    Integer customer_FK;
    Integer booking_FK;
    Integer reservation_FK;

}
