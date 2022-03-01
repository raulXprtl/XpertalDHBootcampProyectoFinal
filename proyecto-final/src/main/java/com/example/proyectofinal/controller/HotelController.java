package com.example.proyectofinal.controller;

import com.example.proyectofinal.dto.CrudResponseDTO;
import com.example.proyectofinal.dto.hotel.*;
import com.example.proyectofinal.service.HotelService;
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
public class HotelController {
    @Autowired
    private HotelService hotelService;

    //-------------------------------ALTA DE HOTEL-------------------------------------
    @PostMapping(path = "/api/v1/hotels/new")
    public ResponseEntity<CrudResponseDTO> saveNewHotel(@RequestBody HotelPostRequestDTO request) {
        return new ResponseEntity<>(this.hotelService.saveNewHotel(request), HttpStatus.OK);
    }

    //---------------------------MODIFICACION DE HOTEL----------------------------------
    @PutMapping(path = "api/v1/hotels/edit")
    public ResponseEntity<CrudResponseDTO> updateHotel(@RequestParam String hotelCode, @RequestBody HotelPostRequestDTO request) {
        return new ResponseEntity<>(this.hotelService.updateHotel(hotelCode, request), HttpStatus.OK);
    }

    //------------------------------BAJA DE UN HOTEL------------------------------------
    @DeleteMapping(path = "/api/v1/hotels/delete")
    public ResponseEntity<CrudResponseDTO> deleteHotel(@RequestParam String hotelCode) {
        return new ResponseEntity<>(this.hotelService.deleteHotel(hotelCode), HttpStatus.OK);
    }

    //-----------------------------CONSULTAS DE HOTELES----------------------------------
    /**
     * This method handles the get request for retrieving all hotels.
     * @return response entity containing a list of hotels.
     */
    @GetMapping(path = "/api/v1/hotels", params = {"!dateFrom", "!dateTo", "!destination"})
    public ResponseEntity<List<HotelDTO>> getHotels() {
        return new ResponseEntity<>(this.hotelService.getHotels(), HttpStatus.OK);
    }

    /**
     * This method handles the get requests for hotels.
     * @param dateFrom string date to filter start of reservation.
     * @param dateTo string date to filter end of reservation.
     * @param destination string to filter hotel location.
     * @return response entity containing a list of hotels.
     */
    @GetMapping(path = "/api/v1/hotels")
    public ResponseEntity<List<HotelDTO>> getAvailableHotels(
            @RequestParam @NotNull @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate dateFrom,
            @RequestParam @NotNull @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate dateTo,
            @RequestParam @NotBlank(message = "El destino elegido no existe") String destination){
        HotelGetRequestDTO request = new HotelGetRequestDTO(dateFrom, dateTo, destination);
        return new ResponseEntity<>(
                this.hotelService.getHotelsAvailable(request), HttpStatus.OK);
    }

    //--------------------------------ALTA DE BOOKING----------------------------------------------
    /**
     * This method handles the post requests for hotel bookings.
     * @param request contains required request parameters.
     * @return response entity containing processed information.
     */
    @PostMapping(path = "/api/v1/booking")
    public ResponseEntity<CrudResponseDTO> postBooking(@Valid @RequestBody BookingPostRequestDTO request) {
        return new ResponseEntity<>(this.hotelService.postBooking(request), HttpStatus.OK);
    }

    //--------------------------------MODIFICACION DE BOOKING----------------------------------------------

    @PutMapping(path = "/api/v1/hotel-booking/edit")
    public ResponseEntity<CrudResponseDTO> updateBooking(@RequestParam Long id, @RequestBody BookingBaseDTO request) {
        return new ResponseEntity<>(this.hotelService.updateBooking(id, request), HttpStatus.OK);
    }

    //-----------------------------------BAJA DE BOOKING--------------------------------------------
    @DeleteMapping(path = "/api/v1/hotel-booking/delete")
    public ResponseEntity<CrudResponseDTO> deleteBooking(@RequestParam Long id) {
        return new ResponseEntity<>(this.hotelService.deleteBooking(id), HttpStatus.OK);
    }

    //---------------------------------CONSULTA DE BOOKING---------------------------------------
    @GetMapping(path = "/api/v1/hotel-bookings")
    public ResponseEntity<List<BookingBaseDTO>> getBookings() {
        return new ResponseEntity<>(this.hotelService.getBookings(), HttpStatus.OK);
    }


}
