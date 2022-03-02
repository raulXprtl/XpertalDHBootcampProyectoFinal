package com.example.proyectofinal.controller;

import com.example.proyectofinal.dto.CrudResponseDTO;
import com.example.proyectofinal.dto.reservation.ReservationPostRequestDTO;
import com.example.proyectofinal.dto.reservation.ReservationRequestDTO;
import com.example.proyectofinal.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/flight-reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    //--------------------------------ALTA DE RESERVACION------------------------------------
    /**
     * This method handles the post requests for flight reservations.
     * @param request contains required request parameters.
     * @return response entity containing processed information.
     */
    @PostMapping(path = "/new")
    public ResponseEntity<CrudResponseDTO> postFlightReservation(@Valid @RequestBody ReservationPostRequestDTO request) {
        return new ResponseEntity<>(this.reservationService.postFlightReservation(request), HttpStatus.OK);
    }

    //----------------------------MODIFICACION DE RESERVACION------------------------------------

    @PutMapping(path = "/edit")
    public ResponseEntity<CrudResponseDTO> updateReservation(@RequestParam Integer id, @RequestBody ReservationPostRequestDTO request) {
        return new ResponseEntity<>(this.reservationService.updateReservation(id, request), HttpStatus.OK);
    }

    //----------------------------BAJA DE RESERVACION------------------------------------
    @DeleteMapping(path = "/delete")
    public ResponseEntity<CrudResponseDTO> deleteReservation(@RequestParam Integer id) {
        return new ResponseEntity<>(this.reservationService.deleteReservation(id), HttpStatus.OK);
    }

    //-------------------------CONSULTA DE RESERVACION-----------------------------------
    @GetMapping(path = "")
    public ResponseEntity<List<ReservationRequestDTO>> getReservations() { // <-- Aqui debemos sacar una lista de entidades CUANDO sean creadas.
        return new ResponseEntity<>(this.reservationService.getReservations(), HttpStatus.OK);
    }
}
