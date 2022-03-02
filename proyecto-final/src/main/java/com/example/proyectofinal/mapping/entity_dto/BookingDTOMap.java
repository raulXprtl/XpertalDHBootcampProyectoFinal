package com.example.proyectofinal.mapping.entity_dto;

import com.example.proyectofinal.dto.Booking.BookingRequestDTO;
import com.example.proyectofinal.entity.Booking;
import org.modelmapper.PropertyMap;

public class BookingDTOMap extends PropertyMap<Booking, BookingRequestDTO> {
    @Override
    protected void configure(){
        using(new PersonToPersonDTOConverter()).map(source.getPeople()).setPeople(null);
//        using(new PaymentToPaymentDTOConverter()).map(source.getPaymentMethod()).setPaymentMethod(null);
    }
}
