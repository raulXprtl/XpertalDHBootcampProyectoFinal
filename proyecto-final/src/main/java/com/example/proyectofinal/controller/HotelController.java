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

@Validated
@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    //-------------------------------ALTA DE HOTEL-------------------------------------
    @PostMapping(path = "/new")
    public ResponseEntity<CrudResponseDTO> saveNewHotel(@Valid @RequestBody HotelDTO request) {
        return new ResponseEntity<>(this.hotelService.saveNewHotel(request), HttpStatus.OK);
    }

    //---------------------------MODIFICACION DE HOTEL----------------------------------
    @PutMapping(path = "/edit")
    public ResponseEntity<CrudResponseDTO> updateHotel(@RequestParam Integer hotelCode, @RequestBody HotelDTO request) {
        return new ResponseEntity<>(this.hotelService.updateHotel(hotelCode, request), HttpStatus.OK);
    }

    //------------------------------BAJA DE UN HOTEL------------------------------------
    @DeleteMapping(path = "/delete")
    public ResponseEntity<CrudResponseDTO> deleteHotel(@RequestParam Integer hotelCode) {
        return new ResponseEntity<>(this.hotelService.deleteHotel(hotelCode), HttpStatus.OK);
    }

    //-----------------------------CONSULTAS DE HOTELES----------------------------------
    /**
     * This method handles the get request for retrieving all hotels.
     * @return response entity containing a list of hotels.
     */
    @GetMapping(path = "", params = {"!dateFrom", "!dateTo", "!destination"})
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
    @GetMapping(path = "")
    public ResponseEntity<List<HotelDTO>> getAvailableHotels(
            @RequestParam @NotNull @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate dateFrom,
            @RequestParam @NotNull @DateTimeFormat(pattern="dd/MM/yyyy") LocalDate dateTo,
            @RequestParam @NotBlank(message = "El destino elegido no existe") String destination){
        HotelGetRequestDTO request = new HotelGetRequestDTO(dateFrom, dateTo, destination);
        return new ResponseEntity<>(
                this.hotelService.getHotelsAvailable(request), HttpStatus.OK);
    }
}
