package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.CrudResponseDTO;
import com.example.proyectofinal.dto.flight.FlightDTO;
import com.example.proyectofinal.entity.Booking;
import com.example.proyectofinal.entity.Flight;
import com.example.proyectofinal.entity.Person;
import com.example.proyectofinal.entity.Reservation;
import com.example.proyectofinal.repository.FlightRepository;
import com.example.proyectofinal.repository.PersonRepository;
import com.example.proyectofinal.repository.ReservationRepository;
import com.example.proyectofinal.util.FlightDataGenerator;
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
class ReservationServiceUnitTest {
    @Mock
    ReservationRepository reservationRepository;
    @Mock
    PersonRepository personRepository;
    @Mock
    FlightRepository flightRepository;

    @InjectMocks
    ReservationServiceImpl service;

    @Test
    void postFlightReservation() {
        List<Flight> flights = FlightDataGenerator.getFlightList(4);
        List<FlightDTO> flightList = FlightDataGenerator.getFlightDTOList(flights);

        when(flightRepository.findAll()).thenReturn(flights);
        when(reservationRepository.save(Mockito.any(Reservation.class)))
                .thenAnswer(i -> i.getArguments()[0]);
        when(personRepository.save(Mockito.any(Person.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        CrudResponseDTO response = service.postFlightReservation(
                FlightDataGenerator.getFlightPostRequest(flightList.get(0), 2, "DEBIT"));

        verify(flightRepository, atLeastOnce()).findAll();
        assertEquals(new CrudResponseDTO("El proceso terminó satisfactoriamente"), response);
    }

    @Test
    void postFlightReservationOriginException() {
        List<Flight> flights = FlightDataGenerator.getFlightList(4);

        when(flightRepository.findAll()).thenReturn(flights);

        FlightDTO flightRequest = new FlightDTO(
                flights.get(0).getFlightNumber(),
                flights.get(0).getName(),
                "Bad Origin",
                flights.get(0).getDestination(),
                flights.get(0).getSeatType(),
                flights.get(0).getFlightPrice(),
                flights.get(0).getGoingDate(),
                flights.get(0).getReturnDate());

        assertThrows(IllegalArgumentException.class, () -> service.postFlightReservation(
                FlightDataGenerator.getFlightPostRequest(flightRequest, 2, "DEBIT")));
        verify(flightRepository, atLeastOnce()).findAll();
    }

    @Test
    void postFlightReservationDestinationException() {
        List<Flight> flights = FlightDataGenerator.getFlightList(4);

        when(flightRepository.findAll()).thenReturn(flights);

        FlightDTO flightRequest = new FlightDTO(
                flights.get(0).getFlightNumber(),
                flights.get(0).getName(),
                flights.get(0).getOrigin(),
                "Bad Destination",
                flights.get(0).getSeatType(),
                flights.get(0).getFlightPrice(),
                flights.get(0).getGoingDate(),
                flights.get(0).getReturnDate());

        assertThrows(IllegalArgumentException.class, () -> service.postFlightReservation(
                FlightDataGenerator.getFlightPostRequest(flightRequest, 2, "DEBIT")));
        verify(flightRepository, atLeastOnce()).findAll();
    }

    @Test
    void postFlightReservationReturnDateException() {
        List<Flight> flights = FlightDataGenerator.getFlightList(4);

        when(flightRepository.findAll()).thenReturn(flights);

        FlightDTO flightRequest = new FlightDTO(
                flights.get(0).getFlightNumber(),
                flights.get(0).getName(),
                flights.get(0).getOrigin(),
                flights.get(0).getDestination(),
                flights.get(0).getSeatType(),
                flights.get(0).getFlightPrice(),
                flights.get(0).getGoingDate(),
                flights.get(0).getReturnDate().plus(1, ChronoUnit.DAYS));

        assertThrows(NoSuchElementException.class, () -> service.postFlightReservation(
                FlightDataGenerator.getFlightPostRequest(flightRequest, 2, "DEBIT")));
        verify(flightRepository, atLeastOnce()).findAll();
    }
}
