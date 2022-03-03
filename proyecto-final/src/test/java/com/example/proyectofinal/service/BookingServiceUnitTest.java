package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.CrudResponseDTO;
import com.example.proyectofinal.dto.hotel.HotelDTO;
import com.example.proyectofinal.entity.Booking;
import com.example.proyectofinal.entity.Hotel;
import com.example.proyectofinal.entity.Person;
import com.example.proyectofinal.repository.BookingRepository;
import com.example.proyectofinal.repository.HotelRepository;
import com.example.proyectofinal.repository.PersonRepository;
import com.example.proyectofinal.util.HotelDataGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class BookingServiceUnitTest {
    @Mock
    BookingRepository bookingRepository;
    @Mock
    PersonRepository personRepository;
    @Mock
    HotelRepository hotelRepository;

    @InjectMocks
    BookingServiceImpl service;

    @Test
    void postBooking() {
        List<Hotel> hotels = HotelDataGenerator.getHotelList(4);
        List<HotelDTO> hotelList = HotelDataGenerator.getHotelDTOList(hotels);

        when(hotelRepository.findAll()).thenReturn(hotels);
        when(bookingRepository.save(Mockito.any(Booking.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        when(personRepository.save(Mockito.any(Person.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        CrudResponseDTO response = service.postBooking(
                HotelDataGenerator.getHotelPostRequest(hotelList.get(0), "DEBIT"));

        verify(hotelRepository, atLeastOnce()).findAll();
        verify(personRepository, atLeastOnce()).save(Mockito.any(Person.class));
        assertEquals(new CrudResponseDTO("El proceso terminó satisfactoriamente"), response);
    }

    @Test
    void postBookingDestinationException() {
        List<Hotel> hotels = HotelDataGenerator.getHotelList(4);

        when(hotelRepository.findAll()).thenReturn(hotels);

        HotelDTO hotelRequest = new HotelDTO(
                hotels.get(0).getHotelCode(),
                hotels.get(0).getName(),
                "Bad Destination",
                hotels.get(0).getRoomType(),
                hotels.get(0).getRoomPrice(),
                hotels.get(0).getDisponibilityDateFrom(),
                hotels.get(0).getDisponibilityDateTo(),
                hotels.get(0).getIsBooking());

        assertThrows(IllegalArgumentException.class, () -> service.postBooking(
                HotelDataGenerator.getHotelPostRequest(hotelRequest, "DEBIT")));
        verify(hotelRepository, atLeastOnce()).findAll();
    }

    @Test
    void postBookingDateOutOfRangeException() {
        List<Hotel> hotels = HotelDataGenerator.getHotelList(4);

        when(hotelRepository.findAll()).thenReturn(hotels);

        HotelDTO hotelRequest = new HotelDTO(
                hotels.get(0).getHotelCode(),
                hotels.get(0).getName(),
                hotels.get(0).getPlace(),
                hotels.get(0).getRoomType(),
                hotels.get(0).getRoomPrice(),
                hotels.get(0).getDisponibilityDateFrom(),
                hotels.get(0).getDisponibilityDateTo().plus(1, ChronoUnit.DAYS),
                hotels.get(0).getIsBooking());

        assertThrows(NoSuchElementException.class, () -> service.postBooking(
                HotelDataGenerator.getHotelPostRequest(hotelRequest, "DEBIT")));
        verify(hotelRepository, atLeastOnce()).findAll();
    }
}
