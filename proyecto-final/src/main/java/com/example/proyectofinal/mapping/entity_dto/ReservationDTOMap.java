package com.example.proyectofinal.mapping.entity_dto;

import com.example.proyectofinal.dto.reservation.ReservationRequestDTO;
import com.example.proyectofinal.entity.Reservation;
import org.modelmapper.PropertyMap;

public class ReservationDTOMap extends PropertyMap<Reservation, ReservationRequestDTO> {
    @Override
    protected void configure(){
        using(new PersonToPersonDTOConverter()).map(source.getPeople()).setPeople(null);
//        using(new PaymentToPaymentDTOConverter()).map(source.getPaymentMethod()).setPaymentMethod(null);
    }
}
