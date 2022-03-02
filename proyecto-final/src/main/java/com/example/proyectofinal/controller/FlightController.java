package com.example.proyectofinal.controller;

import com.example.proyectofinal.dto.CrudResponseDTO;
import com.example.proyectofinal.dto.flight.*;
import com.example.proyectofinal.service.FlightService;
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
@RequestMapping("/api/v1/flights")
public class FlightController {
    @Autowired
    private FlightService flightService;

    //-------------------------------ALTA DE VUELO-------------------------------------
    @PostMapping(path = "/new")
    public ResponseEntity<CrudResponseDTO> saveNewFlight(@Valid @RequestBody FlightDTO request) {
        return new ResponseEntity<>(this.flightService.saveNewFlight(request), HttpStatus.OK);
    }

    //---------------------------MODIFICACION DE VUELO----------------------------------
    @PutMapping(path = "/edit")
    public ResponseEntity<CrudResponseDTO> updateFlight(@RequestParam Integer flightNumber, @RequestBody FlightDTO request) {
        return new ResponseEntity<>(this.flightService.updateFlight(flightNumber, request), HttpStatus.OK);
    }

    //------------------------------BAJA DE UN VUELO------------------------------------
    @DeleteMapping(path = "/delete")
    public ResponseEntity<CrudResponseDTO> deleteFlight(@RequestParam Integer flightNumber) {
        return new ResponseEntity<>(this.flightService.deleteFlight(flightNumber), HttpStatus.OK);
    }

    //---------------------------CONSULTAS DE VUELO-------------------------------------
    /**
     * This method handles the get request for retrieving all flights.
     * @return response entity containing a list of flights.
     */
    @GetMapping(path = "", params = {"!dateFrom", "!dateTo", "!origin", "!destination"})
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
    @GetMapping(path = "")
    public ResponseEntity<List<FlightDTO>> getAvailableFlights(
            @RequestParam @NotNull @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate dateFrom,
            @RequestParam @NotNull @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate dateTo,
            @RequestParam @NotBlank(message = "El origen elegido no existe") String origin,
            @RequestParam @NotBlank(message = "El destino elegido no existe") String destination) {
        FlightGetRequestDTO request = new FlightGetRequestDTO(dateFrom, dateTo, origin, destination);
        return new ResponseEntity<>(
                this.flightService.getFlightsAvailable(request), HttpStatus.OK);
    }
}

