package com.example.proyectofinal.controller;

import com.example.proyectofinal.dto.CrudResponseDTO;
import com.example.proyectofinal.dto.flight.*;
import com.example.proyectofinal.service.FlightService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@RestController
@Validated
public class FlightController {
    @Autowired
    private FlightService flightService;

    //-------------------------------ALTA DE VUELO-------------------------------------
    @PostMapping(path = "/api/v1/hotels/new")
    public ResponseEntity<CrudResponseDTO> saveNewFlight(@RequestBody FlightNewPostRequestDTO request) {
        return new ResponseEntity<>(this.flightService.saveNewFlight(request), HttpStatus.OK);
    }

    //---------------------------MODIFICACION DE VUELO----------------------------------
    @PutMapping(path = "api/v1/flights/edit")
    public ResponseEntity<CrudResponseDTO> updateFlight(@RequestParam String flightNumber, @RequestBody FlightNewPostRequestDTO request) {
        return new ResponseEntity<>(this.flightService.updateFlight(flightNumber, request), HttpStatus.OK);
    }

    //------------------------------BAJA DE UN VUELO------------------------------------
    @DeleteMapping(path = "/api/v1/flights/delete")
    public ResponseEntity<CrudResponseDTO> deleteFlight(@RequestParam String flightNumber) {
        return new ResponseEntity<>(this.flightService.deleteFlight(flightNumber), HttpStatus.OK);
    }

    //---------------------------CONSULTAS DE VUELO-------------------------------------
    /**
     * This method handles the get request for retrieving all flights.
     * @return response entity containing a list of flights.
     */
    @GetMapping(path = "/api/v1/flights", params = {"!dateFrom", "!dateTo", "!origin", "!destination"})
    public ResponseEntity<List<FlightDTO>> getFlights() {
        return new ResponseEntity<>(flightService.getFlights(), HttpStatus.OK);
    }

    /**
     * This method handles the get requests for flights.
     * @param dateFrom date to filter outbound flight date.
     * @param dateTo date to filter return flight date
     * @param origin string to filter flight origin.
     * @param destination string to filter flight destination.
     * @return response entity containing a list of flights.
     */
    @GetMapping(path = "/api/v1/flights")
    public ResponseEntity<List<FlightDTO>> getAvailableFlights(
            @RequestParam @NotNull @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate dateFrom,
            @RequestParam @NotNull @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate dateTo,
            @RequestParam @NotBlank(message = "El origen elegido no existe") String origin,
            @RequestParam @NotBlank(message = "El destino elegido no existe") String destination) {
        FlightGetRequestDTO request = new FlightGetRequestDTO(dateFrom, dateTo, origin, destination);
        return new ResponseEntity<>(
                this.flightService.getFlightsAvailable(request), HttpStatus.OK);
    }

    //--------------------------------ALTA DE RESERVACION------------------------------------
    /**
     * This method handles the post requests for flight reservations.
     * @param request contains required request parameters.
     * @return response entity containing processed information.
     */
    @PostMapping(path = "/api/v1/flight-reservation/new")
    public ResponseEntity<CrudResponseDTO> postFlightReservation(@Valid @RequestBody FlightPostRequestDTO request) {
        return new ResponseEntity<>(this.flightService.postFlightReservation(request), HttpStatus.OK);
    }


    //----------------------------MODIFICACION DE RESERVACION------------------------------------

    @PutMapping(path = "/api/v1/flight-reservation/edit")
    public ResponseEntity<CrudResponseDTO> updateReservation(@RequestParam Long id, @RequestBody ReservationUpdateDTO request) {
        return new ResponseEntity<>(this.flightService.updateReservation(id, request), HttpStatus.OK);
    }

    //----------------------------BAJA DE RESERVACION------------------------------------
    @DeleteMapping(path = "/api/v1/flight-reservation/delete")
    public ResponseEntity<CrudResponseDTO> deleteReservation(@RequestParam Long id) {
        return new ResponseEntity<>(this.flightService.deleteReservation(id), HttpStatus.OK);
    }

    //-------------------------CONSULTA DE RESERVACION-----------------------------------
    @GetMapping(path = "/api/v1/flight-reservations")
    public ResponseEntity<List<ReservationDTO>> getReservations() { // <-- Aqui debemos sacar una lista de entidades CUANDO sean creadas.
        return new ResponseEntity<>(this.flightService.getReservations(), HttpStatus.OK);
    }

}

