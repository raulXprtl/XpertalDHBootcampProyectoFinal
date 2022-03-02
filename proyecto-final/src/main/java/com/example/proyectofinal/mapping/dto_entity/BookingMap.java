package com.example.proyectofinal.mapping.dto_entity;

import com.example.proyectofinal.dto.booking.BookingRequestDTO;
import com.example.proyectofinal.entity.Booking;
import org.modelmapper.PropertyMap;

public class BookingMap extends PropertyMap<BookingRequestDTO, Booking> {
    @Override
    protected void configure(){
        using(new PersonDTOToPersonConverter()).map(source.getPeople()).setPeople(null);
        using(new PaymentDTOToPaymentConverter()).map(source.getPaymentMethod()).setPaymentMethod(null);
    }
}
