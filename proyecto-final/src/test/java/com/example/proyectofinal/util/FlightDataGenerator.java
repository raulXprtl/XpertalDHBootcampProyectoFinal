package com.example.proyectofinal.util;

import com.example.proyectofinal.dto.PaymentMethodDTO;
import com.example.proyectofinal.dto.PersonDTO;
import com.example.proyectofinal.dto.StatusCodeDTO;
import com.example.proyectofinal.dto.flight.*;
import com.example.proyectofinal.entity.Flight;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FlightDataGenerator {
    public static Flight getFlightTest(int code, String origin, String destination){
        Flight flight = new Flight();
        flight.setFlightNumber(String.format("TEFL-1%02d", code % 10));
        flight.setOrigin(origin);
        flight.setDestination(destination);
        flight.setSeatType("Economy");
        flight.setPricePerPerson(8.0);
        flight.setDateFrom(LocalDate.of(2022, 2, 10));
        flight.setDateTo(LocalDate.of(2022, 2, 20));
        return flight;
    }

    public static List<Flight> getFlightList(int count) {
        List<Flight> flightList = new ArrayList<>();
        for (int ii = 0; ii < count; ii++) {
            flightList.add(getFlightTest(
                    ii,
                    String.format("Test Orig. %d", ii),
                    String.format("Test Dest. %d", ii)));
        }
        return flightList;
    }

    public static List<FlightDTO> getFlightDTOList(List<Flight> flights) {
        List<FlightDTO> flightList = new ArrayList<>();
        flights.forEach(flight -> flightList.add(new FlightDTO(
                flight.getFlightNumber(),
                flight.getOrigin(),
                flight.getDestination(),
                flight.getSeatType(),
                flight.getPricePerPerson(),
                flight.getDateFrom(),
                flight.getDateTo())));
        return flightList;
    }

    private static FlightReservationBaseDTO getReservationBase(FlightDTO flight,
                                                               BigDecimal seats,
                                                               List<PersonDTO> people) {
        return new FlightReservationBaseDTO(
                flight.getDateFrom(),
                flight.getDateTo(),
                flight.getOrigin(),
                flight.getDestination(),
                flight.getFlightNumber(),
                seats,
                flight.getSeatType(),
                people);
    }

    private static FlightReservationRequestDTO getReservationRequest(FlightDTO flight,
                                                                     BigDecimal seats,
                                                                     List<PersonDTO> people,
                                                                     PaymentMethodDTO paymentMethod) {
        FlightReservationBaseDTO reservation = getReservationBase(flight, seats, people);
        return new FlightReservationRequestDTO(reservation, paymentMethod);
    }

    public static FlightPostResponseDTO getFlightPostResponse(FlightDTO flight, int seats, double totalCost, double interest) {
        List<PersonDTO> people = new ArrayList<>();
        for (int ii = 0; ii < seats; ii++)
            people.add(new PersonDTO());

        return new FlightPostResponseDTO(
                "test@digitalhouse.com",
                totalCost,
                interest,
                totalCost * (1 + interest / 100),
                getReservationBase(flight, new BigDecimal(seats), people),
                new StatusCodeDTO(HttpStatus.OK.value(),
                        "El proceso terminó satisfactoriamente"));
    }

    public static FlightPostRequestDTO getFlightPostRequest(FlightDTO flight, int seats, String paymentType) {
        List<PersonDTO> people = new ArrayList<>();
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

        FlightPostRequestDTO request = new FlightPostRequestDTO();
        request.setUserName("test@digitalhouse.com");
        request.setFlightReservation(getReservationRequest(flight, new BigDecimal(seats), people, paymentMethod));

        return request;
    }
}
