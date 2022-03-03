package com.example.proyectofinal.util;

import com.example.proyectofinal.dto.PaymentMethodDTO;
import com.example.proyectofinal.dto.PersonDTO;
import com.example.proyectofinal.dto.StatusCodeDTO;
import com.example.proyectofinal.dto.flight.*;
import com.example.proyectofinal.dto.reservation.ReservationBaseDTO;
import com.example.proyectofinal.dto.reservation.ReservationPostRequestDTO;
import com.example.proyectofinal.dto.reservation.ReservationPostResponseDTO;
import com.example.proyectofinal.dto.reservation.ReservationRequestDTO;
import com.example.proyectofinal.entity.Flight;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FlightDataGenerator {
    public static Flight getFlightTest(String origin, String destination){
        Flight flight = new Flight();
        flight.setOrigin(origin);
        flight.setDestination(destination);
        flight.setSeatType("Economy");
        flight.setFlightPrice(8.0);
        flight.setGoingDate(LocalDate.of(2022, 2, 10));
        flight.setReturnDate(LocalDate.of(2022, 2, 20));
        return flight;
    }

    public static List<Flight> getFlightList(int count) {
        List<Flight> flightList = new ArrayList<>();
        for (int ii = 0; ii < count; ii++) {
            flightList.add(getFlightTest(
                    String.format("Test Orig. %d", ii),
                    String.format("Test Dest. %d", ii)));
        }
        return flightList;
    }

    public static List<FlightDTO> getFlightDTOList(List<Flight> flights) {
        List<FlightDTO> flightList = new ArrayList<>();
        flights.forEach(flight -> flightList.add(new FlightDTO(
                flight.getFlightNumber(),
                flight.getName(),
                flight.getOrigin(),
                flight.getDestination(),
                flight.getSeatType(),
                flight.getFlightPrice(),
                flight.getGoingDate(),
                flight.getReturnDate())));
        return flightList;
    }

    private static ReservationBaseDTO getReservationBase(FlightDTO flight,
                                                         BigDecimal seats,
                                                         Set<PersonDTO> people) {
        return new ReservationBaseDTO(
                flight.getFlightNumber(),
                flight.getGoingDate(),
                flight.getReturnDate(),
                flight.getOrigin(),
                flight.getDestination(),
                flight.getFlightNumber(),
                seats,
                flight.getSeatType(),
                people);
    }

    private static ReservationRequestDTO getReservationRequest(FlightDTO flight,
                                                               BigDecimal seats,
                                                               Set<PersonDTO> people,
                                                               PaymentMethodDTO paymentMethod) {
        ReservationBaseDTO reservation = getReservationBase(flight, seats, people);
        return new ReservationRequestDTO(reservation, paymentMethod);
    }

    public static ReservationPostRequestDTO getFlightPostRequest(FlightDTO flight, int seats, String paymentType) {
        Set<PersonDTO> people = new HashSet<>();
        for (int ii = 0; ii < seats; ii++)
            people.add(new PersonDTO());

        PaymentMethodDTO paymentMethod;
        if (paymentType.equalsIgnoreCase("DEBIT")) {
            paymentMethod = new PaymentMethodDTO(
                    paymentType,
                    "1234-1234-1234-1234",
                    BigDecimal.ONE);
        } else {
            paymentMethod = new PaymentMethodDTO(
                    "CREDIT",
                    "1234-1234-1234-1234",
                    new BigDecimal(3));
        }

        ReservationPostRequestDTO request = new ReservationPostRequestDTO();
        request.setUserName("test@digitalhouse.com");
        request.setFlightReservation(getReservationRequest(flight, new BigDecimal(seats), people, paymentMethod));

        return request;
    }
}
