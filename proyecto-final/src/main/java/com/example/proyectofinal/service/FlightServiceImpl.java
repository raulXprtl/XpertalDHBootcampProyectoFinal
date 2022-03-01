package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.CrudResponseDTO;
import com.example.proyectofinal.dto.StatusCodeDTO;
import com.example.proyectofinal.dto.flight.*;
import com.example.proyectofinal.entity.Flight;
import com.example.proyectofinal.repository.FlightRepository;
import com.example.proyectofinal.util.StringUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FlightServiceImpl implements FlightService {
    @Autowired
    private FlightRepository flightRepository;
    private ModelMapper modelMapper = new ModelMapper();

    /**
     * This method gets a list of flights from the local repository.
     * @return list of flights.
     */
    @Override
    public List<FlightDTO> getFlights() {
        List<FlightDTO> flights = new ArrayList<>();
        this.flightRepository.findAll().forEach(flight -> flights.add(
                modelMapper.map(flight, FlightDTO.class)));
        if (flights.size() == 0)
            throw new NoSuchElementException("No se encontraron vuelos");
        return flights;
    }

    /**
     * This method creates a list of FlightDTO containing available flights.
     * @param request contains dateTo, dateFrom, origin and destination parameters.
     * @return list of available flights.
     */
    @Override
    public List<FlightDTO> getFlightsAvailable(FlightGetRequestDTO request) {
        Assert.isTrue(request.getDateTo().compareTo(request.getDateFrom()) >= 0,
                "La fecha de ida debe ser menor a la de vuelta");

        List<FlightDTO> flights = new ArrayList<>();
        Stream<Flight> flightStream = filterFlights(
                request.getDateFrom(),
                request.getDateTo(),
                request.getOrigin(),
                request.getDestination(),
                false
        );

        flightStream.forEach(flight -> flights.add(
                modelMapper.map(flight, FlightDTO.class)));
        return flights;
    }

    /**
     * This method returns a reponse for a flight reservation request.
     * @param request contains required request parameters.
     * @return response object.
     */
    @Override
    public CrudResponseDTO postFlightReservation(FlightPostRequestDTO request) {
        // Filter flights with request parameters.
        Stream<Flight> flightStream = filterFlights(
                request.getFlightReservation().getDateFrom(),
                request.getFlightReservation().getDateTo(),
                request.getFlightReservation().getOrigin(),
                request.getFlightReservation().getDestination(),
                true);

        if (request.getFlightReservation().getFlightNumber() != null)
            flightStream = flightStream.filter(flight -> flight.getFlightNumber()
                    .equalsIgnoreCase(request.getFlightReservation().getFlightNumber()));
        if (request.getFlightReservation().getSeatType() != null)
            flightStream = flightStream.filter(flight -> flight.getSeatType()
                    .equalsIgnoreCase(request.getFlightReservation().getSeatType()));

        Optional<Flight> flightOptional = flightStream.findFirst();

        return new CrudResponseDTO("El proceso terminó satisfactoriamente");
    }

    @Override
    public CrudResponseDTO saveNewFlight(FlightNewPostRequestDTO request) {
        return null;
    }

    @Override
    public CrudResponseDTO updateFlight(String flightNumber, FlightNewPostRequestDTO request) {
        return null;
    }

    @Override
    public CrudResponseDTO deleteFlight(String flightNumber) {
        return null;
    }

    @Override
    public CrudResponseDTO updateReservation(Long idReservation, ReservationUpdateDTO request) {
        return null;
    }

    @Override
    public CrudResponseDTO deleteReservation(Long idReservation) {
        return null;
    }

    @Override
    public List<ReservationDTO> getReservations() {
        return null;
    }

    /**
     * This method filters a stream of flights with the basic request parameters.
     * @param dateFrom date to filter outbound flight date.
     * @param dateTo date to filter return flight date.
     * @param origin string to filter flight origin.
     * @param destination string to filter flight destination.
     * @param postRequest boolean stating if used in get or post method.
     * @return a stream of flights.
     */
    private Stream<Flight> filterFlights(LocalDate dateFrom, LocalDate dateTo, String origin,
            String destination, boolean postRequest) {
        Stream<Flight> flightStream;

        if (origin != null || postRequest) {
            flightStream = this.flightRepository.findAll().stream();
            flightStream = flightStream.filter(flight -> StringUtil.normalizeString(flight.getOrigin())
                    .equalsIgnoreCase(StringUtil.normalizeString(origin)));
            Assert.isTrue(flightStream.findAny().isPresent(), "El origen elegido no existe");
        }

        if (destination != null || postRequest) {
            flightStream = this.flightRepository.findAll().stream()
                    .filter(flight -> StringUtil.normalizeString(flight.getDestination())
                    .equalsIgnoreCase(StringUtil.normalizeString(destination)));
            Assert.isTrue(flightStream.findAny().isPresent(), "El destino elegido no existe");
        }

        if ( origin != null || destination != null || postRequest) {
            flightStream = this.flightRepository.findAll().stream()
                    .filter(flight -> StringUtil.normalizeString(flight.getOrigin())
                            .equalsIgnoreCase(StringUtil.normalizeString(origin)) &&
                            StringUtil.normalizeString(flight.getDestination())
                                    .equalsIgnoreCase(StringUtil.normalizeString(destination)));

            Assert.isTrue(flightStream.findAny().isPresent(), "La combinación de origen y destino seleccionados no existe");
        }

        flightStream = this.flightRepository.findAll().stream()
                .filter(flight -> StringUtil.normalizeString(flight.getOrigin())
                        .equalsIgnoreCase(StringUtil.normalizeString(origin)) &&
                        StringUtil.normalizeString(flight.getDestination())
                                .equalsIgnoreCase(StringUtil.normalizeString(destination)));

        if (dateFrom != null || postRequest)
            flightStream = flightStream.filter(flight -> flight.getDateFrom().equals(dateFrom));
        if (dateTo != null || postRequest)
            flightStream = flightStream.filter(flight -> flight.getDateTo().equals(dateTo));
        return flightStream;
    }
}
