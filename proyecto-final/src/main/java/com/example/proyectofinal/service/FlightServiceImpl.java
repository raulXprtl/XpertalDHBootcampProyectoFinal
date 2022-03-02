package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.CrudResponseDTO;
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

    @Override
    public CrudResponseDTO saveNewFlight(FlightDTO request) {
        this.flightRepository.save(modelMapper.map(request, Flight.class));
        return new CrudResponseDTO("El proceso terminó satisfactoriamente");
    }

    @Override
    public CrudResponseDTO updateFlight(Integer flightNumber, FlightDTO request) {
        Flight flight = this.flightRepository.findById(flightNumber).get();
        if (request.getName() != null)
            flight.setName(request.getName());
        if (request.getOrigin() != null)
            flight.setOrigin(request.getOrigin());
        if (request.getDestination() != null)
            flight.setDestination(request.getDestination());
        if (request.getSeatType() != null)
            flight.setSeatType(request.getSeatType());
        if (request.getFlightPrice() != null)
            flight.setFlightPrice(request.getFlightPrice());
        if (request.getGoingDate() != null)
            flight.setGoingDate(request.getGoingDate());
        if (request.getReturnDate() != null)
            flight.setReturnDate(request.getReturnDate());

        this.flightRepository.save(flight);
        return new CrudResponseDTO("El proceso terminó satisfactoriamente");
    }

    @Override
    public CrudResponseDTO deleteFlight(Integer flightNumber) {
        this.flightRepository.deleteById(flightNumber);
        return new CrudResponseDTO("El proceso terminó satisfactoriamente");

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
            flightStream = flightStream.filter(flight -> flight.getGoingDate().equals(dateFrom));
        if (dateTo != null || postRequest)
            flightStream = flightStream.filter(flight -> flight.getReturnDate().equals(dateTo));
        return flightStream;
    }
}
