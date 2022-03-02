package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.CrudResponseDTO;
import com.example.proyectofinal.dto.reservation.ReservationPostRequestDTO;
import com.example.proyectofinal.dto.reservation.ReservationRequestDTO;
import com.example.proyectofinal.entity.*;
import com.example.proyectofinal.mapping.dto_entity.ReservationMap;
import com.example.proyectofinal.mapping.entity_dto.ReservationDTOMap;
import com.example.proyectofinal.repository.FlightRepository;
import com.example.proyectofinal.repository.PersonRepository;
import com.example.proyectofinal.repository.ReservationRepository;
import com.example.proyectofinal.util.StringUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    private ModelMapper modelMapper = new ModelMapper();

    /**
     * This method returns a reponse for a flight reservation request.
     * @param request contains required request parameters.
     * @return response object.
     */
    @Override
    public CrudResponseDTO postFlightReservation(ReservationPostRequestDTO request) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new ReservationMap());

        // Filter flights with request parameters.
        Stream<Flight> flightStream = filterFlights(
                request.getFlightReservation().getGoingDate(),
                request.getFlightReservation().getReturnDate(),
                request.getFlightReservation().getOrigin(),
                request.getFlightReservation().getDestination(),
                true);

        if (request.getFlightReservation().getFlightNumber() != null)
            flightStream = flightStream.filter(flight -> flight.getFlightNumber()
                    .equals(request.getFlightReservation().getFlightNumber()));
        if (request.getFlightReservation().getSeatType() != null)
            flightStream = flightStream.filter(flight -> flight.getSeatType()
                    .equalsIgnoreCase(request.getFlightReservation().getSeatType()));

        Optional<Flight> flightOptional = flightStream.findFirst();

        // Saves new item to Reservation table.
        Reservation reservation = modelMapper.map(request.getFlightReservation(), Reservation.class);
        reservation.setFlight(flightOptional.get());
        reservation.getPeople().forEach(person -> this.personRepository.save(person));
        this.reservationRepository.save(reservation);

        return new CrudResponseDTO("El proceso terminó satisfactoriamente");
    }

    @Override
    public CrudResponseDTO updateReservation(Integer idReservation, ReservationPostRequestDTO request) {
        ModelMapper modelMapper = new ModelMapper();
        Reservation reservation = this.reservationRepository.findById(idReservation).get();

        if (request.getFlightReservation().getFlightNumber() != null)
            reservation.setFlight(this.flightRepository.findById(request.getFlightReservation().getFlightNumber()).get());
        if (request.getFlightReservation().getPaymentMethod() != null) {
            reservation.setPaymentMethod(modelMapper.map(
                    request.getFlightReservation().getPaymentMethod(), PaymentMethod.class));
        }
        if (request.getFlightReservation().getPeople() != null) {
            Set<Person> people = new HashSet<>();
            request.getFlightReservation().getPeople().forEach(person -> people.add(
                    modelMapper.map(person, Person.class)));
            people.forEach(person -> this.personRepository.save(person));
            reservation.setPeople(people);
        }
        this.reservationRepository.save(reservation);
        return new CrudResponseDTO("El proceso terminó satisfactoriamente");

    }

    @Override
    public CrudResponseDTO deleteReservation(Integer idReservation) {
        this.reservationRepository.deleteById(idReservation);
        return new CrudResponseDTO("El proceso terminó satisfactoriamente");
    }

    @Override
    public List<ReservationRequestDTO> getReservations() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new ReservationDTOMap());

        List<ReservationRequestDTO> requests = new ArrayList<>();

        List<Reservation> reservations = this.reservationRepository.findAll();
        for (Reservation reservation : reservations) {
            ReservationRequestDTO request = modelMapper.map(reservation, ReservationRequestDTO.class);
            request.setOrigin(reservation.getFlight().getOrigin());
            request.setDestination(reservation.getFlight().getDestination());
            request.setGoingDate(reservation.getFlight().getGoingDate());
            request.setReturnDate(reservation.getFlight().getReturnDate());
            request.setFlightNumber(reservation.getFlight().getFlightNumber());
            request.setSeats(new BigDecimal(reservation.getPeople().size()));
            request.setSeatType(reservation.getFlight().getSeatType());
            requests.add(request);
        }
        if (requests.size() == 0)
            throw new NoSuchElementException("No se encontraron hoteles");
        return requests;
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
