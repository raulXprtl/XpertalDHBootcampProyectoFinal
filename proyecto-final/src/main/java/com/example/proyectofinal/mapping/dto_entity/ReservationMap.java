package com.example.proyectofinal.mapping.dto_entity;

import com.example.proyectofinal.dto.reservation.ReservationRequestDTO;
import com.example.proyectofinal.entity.Reservation;
import org.modelmapper.PropertyMap;

public class ReservationMap extends PropertyMap<ReservationRequestDTO, Reservation> {
    @Override
    protected void configure(){
        using(new PersonDTOToPersonConverter()).map(source.getPeople()).setPeople(null);
        using(new PaymentDTOToPaymentConverter()).map(source.getPaymentMethod()).setPaymentMethod(null);
    }
}
