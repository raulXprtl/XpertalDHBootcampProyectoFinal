package com.example.proyectofinal.controller;

import com.example.proyectofinal.dto.Booking.BookingRequestDTO;
import com.example.proyectofinal.dto.CrudResponseDTO;
import com.example.proyectofinal.dto.Booking.BookingBaseDTO;
import com.example.proyectofinal.dto.Booking.BookingPostRequestDTO;
import com.example.proyectofinal.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/hotel-booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    //--------------------------------ALTA DE BOOKING----------------------------------------------
    /**
     * This method handles the post requests for hotel bookings.
     * @param request contains required request parameters.
     * @return response entity containing processed information.
     */
    @PostMapping(path = "/new")
    public ResponseEntity<CrudResponseDTO> postBooking(@Valid @RequestBody BookingPostRequestDTO request) {
        return new ResponseEntity<>(this.bookingService.postBooking(request), HttpStatus.OK);
    }

    //--------------------------------MODIFICACION DE BOOKING----------------------------------------------

    @PutMapping(path = "/edit")
    public ResponseEntity<CrudResponseDTO> updateBooking(@RequestParam Integer id, @RequestBody BookingPostRequestDTO request) {
        return new ResponseEntity<>(this.bookingService.updateBooking(id, request), HttpStatus.OK);
    }

    //-----------------------------------BAJA DE BOOKING--------------------------------------------
    @DeleteMapping(path = "/delete")
    public ResponseEntity<CrudResponseDTO> deleteBooking(@RequestParam Integer id) {
        return new ResponseEntity<>(this.bookingService.deleteBooking(id), HttpStatus.OK);
    }

    //---------------------------------CONSULTA DE BOOKING---------------------------------------
    @GetMapping(path = "")
    public ResponseEntity<List<BookingRequestDTO>> getBookings() {
        return new ResponseEntity<>(this.bookingService.getBookings(), HttpStatus.OK);
    }
}
