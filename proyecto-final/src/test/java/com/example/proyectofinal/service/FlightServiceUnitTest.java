package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.flight.FlightDTO;
import com.example.proyectofinal.dto.flight.FlightGetRequestDTO;
import com.example.proyectofinal.dto.flight.FlightPostResponseDTO;
import com.example.proyectofinal.entity.Flight;
import com.example.proyectofinal.repository.FlightRepository;
import com.example.proyectofinal.util.FlightDataGenerator;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceUnitTest {
    @Mock
    FlightRepository repo;

    @InjectMocks
    FlightServiceImpl service;

    @Test
    void getFlights() {
        List<Flight> flights = FlightDataGenerator.getFlightList(4);
        List<FlightDTO> flightList = FlightDataGenerator.getFlightDTOList(flights);

        when(repo.findAll()).thenReturn(flights);

        List<FlightDTO> getFlights = service.getFlights();

        verify(repo, atLeastOnce()).findAll();
        assertTrue(CollectionUtils.isEqualCollection(flightList, getFlights));
    }

    @Test
    void getFlightsEmpty() {
        List<Flight> flights = new ArrayList<>();

        when(repo.findAll()).thenReturn(flights);

        assertThrows(NoSuchElementException.class, () -> service.getFlights());
        verify(repo, atLeastOnce()).findAll();
    }

    @Test
    void getFlightsAvailable() {
        List<Flight> flights = FlightDataGenerator.getFlightList(4);
        List<FlightDTO> flightListAvailable = new ArrayList<>();
        flightListAvailable.add(new FlightDTO(
                flights.get(0).getFlightNumber(),
                flights.get(0).getOrigin(),
                flights.get(0).getDestination(),
                flights.get(0).getSeatType(),
                flights.get(0).getPricePerPerson(),
                flights.get(0).getDateFrom(),
                flights.get(0).getDateTo()));

        when(repo.findAll()).thenReturn(flights);

        FlightGetRequestDTO request = new FlightGetRequestDTO(
                flightListAvailable.get(0).getDateFrom(),
                flightListAvailable.get(0).getDateTo(),
                flightListAvailable.get(0).getOrigin(),
                flightListAvailable.get(0).getDestination());

        List<FlightDTO> flightsAvailable = service.getFlightsAvailable(request);

        verify(repo, atLeastOnce()).findAll();
        assertTrue(CollectionUtils.isEqualCollection(flightListAvailable, flightsAvailable));
    }

    @Test
    void getFlightsAvailableOriginException() {
        List<Flight> flights = FlightDataGenerator.getFlightList(4);
        List<FlightDTO> flightListAvailable = new ArrayList<>();
        flightListAvailable.add(new FlightDTO(
                flights.get(0).getFlightNumber(),
                flights.get(0).getOrigin(),
                flights.get(0).getDestination(),
                flights.get(0).getSeatType(),
                flights.get(0).getPricePerPerson(),
                flights.get(0).getDateFrom(),
                flights.get(0).getDateTo()));

        FlightGetRequestDTO request = new FlightGetRequestDTO(
                flightListAvailable.get(0).getDateFrom(),
                flightListAvailable.get(0).getDateTo(),
                "Bad Origin",
                flightListAvailable.get(0).getDestination());

        when(repo.findAll()).thenReturn(flights);

        assertThrows(IllegalArgumentException.class, () -> service.getFlightsAvailable(request));
        verify(repo, atLeastOnce()).findAll();
    }

    @Test
    void getFlightsAvailableDestinationException() {
        List<Flight> flights = FlightDataGenerator.getFlightList(4);
        List<FlightDTO> flightListAvailable = new ArrayList<>();
        flightListAvailable.add(new FlightDTO(
                flights.get(0).getFlightNumber(),
                flights.get(0).getOrigin(),
                flights.get(0).getDestination(),
                flights.get(0).getSeatType(),
                flights.get(0).getPricePerPerson(),
                flights.get(0).getDateFrom(),
                flights.get(0).getDateTo()));

        FlightGetRequestDTO request = new FlightGetRequestDTO(
                flightListAvailable.get(0).getDateFrom(),
                flightListAvailable.get(0).getDateTo(),
                flightListAvailable.get(0).getOrigin(),
                "Bad Destination");

        when(repo.findAll()).thenReturn(flights);

        assertThrows(IllegalArgumentException.class, () -> service.getFlightsAvailable(request));
        verify(repo, atLeastOnce()).findAll();
    }

    @Test
    void getFlightsAvailableDateMatchException() {
        List<Flight> flights = FlightDataGenerator.getFlightList(4);

        FlightGetRequestDTO request = new FlightGetRequestDTO(
                flights.get(0).getDateTo(),
                flights.get(0).getDateFrom(),
                flights.get(0).getOrigin(),
                flights.get(0).getDestination());

        assertThrows(IllegalArgumentException.class, () -> service.getFlightsAvailable(request));
    }

    @Test
    void postFlightReservation() {
        List<Flight> flights = FlightDataGenerator.getFlightList(4);
        List<FlightDTO> flightList = FlightDataGenerator.getFlightDTOList(flights);

        FlightPostResponseDTO expectedResponse = FlightDataGenerator.getFlightPostResponse(
                flightList.get(0), 2, 16.0, 0.0);

        when(repo.findAll()).thenReturn(flights);

        FlightPostResponseDTO response = service.postFlightReservation(
                FlightDataGenerator.getFlightPostRequest(flightList.get(0), 2, "DEBIT"));

        verify(repo, atLeastOnce()).findAll();
        assertEquals(expectedResponse, response);
    }

    @Test
    void postFlightReservationOriginException() {
        List<Flight> flights = FlightDataGenerator.getFlightList(4);

        when(repo.findAll()).thenReturn(flights);

        FlightDTO flightRequest = new FlightDTO(
                flights.get(0).getFlightNumber(),
                "Bad Origin",
                flights.get(0).getDestination(),
                flights.get(0).getSeatType(),
                flights.get(0).getPricePerPerson(),
                flights.get(0).getDateFrom(),
                flights.get(0).getDateTo());

        assertThrows(IllegalArgumentException.class, () -> service.postFlightReservation(
                FlightDataGenerator.getFlightPostRequest(flightRequest, 2, "DEBIT")));
        verify(repo, atLeastOnce()).findAll();
    }

    @Test
    void postFlightReservationDestinationException() {
        List<Flight> flights = FlightDataGenerator.getFlightList(4);

        when(repo.findAll()).thenReturn(flights);

        FlightDTO flightRequest = new FlightDTO(
                flights.get(0).getFlightNumber(),
                flights.get(0).getOrigin(),
                "Bad Destination",
                flights.get(0).getSeatType(),
                flights.get(0).getPricePerPerson(),
                flights.get(0).getDateFrom(),
                flights.get(0).getDateTo());

        assertThrows(IllegalArgumentException.class, () -> service.postFlightReservation(
                FlightDataGenerator.getFlightPostRequest(flightRequest, 2, "DEBIT")));
        verify(repo, atLeastOnce()).findAll();
    }

    @Test
    void postFlightReservationReturnDateException() {
        List<Flight> flights = FlightDataGenerator.getFlightList(4);

        when(repo.findAll()).thenReturn(flights);

        FlightDTO flightRequest = new FlightDTO(
                flights.get(0).getFlightNumber(),
                flights.get(0).getOrigin(),
                flights.get(0).getDestination(),
                flights.get(0).getSeatType(),
                flights.get(0).getPricePerPerson(),
                flights.get(0).getDateFrom(),
                flights.get(0).getDateTo().plus(1, ChronoUnit.DAYS));

        assertThrows(NoSuchElementException.class, () -> service.postFlightReservation(
                FlightDataGenerator.getFlightPostRequest(flightRequest, 2, "DEBIT")));
        verify(repo, atLeastOnce()).findAll();
    }
}
