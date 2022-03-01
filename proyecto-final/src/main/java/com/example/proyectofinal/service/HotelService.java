package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.CrudResponseDTO;
import com.example.proyectofinal.dto.hotel.*;

import java.util.List;

public interface HotelService {
    List<HotelDTO> getHotels();

    List<HotelDTO> getHotelsAvailable(HotelGetRequestDTO request);

    CrudResponseDTO postBooking(BookingPostRequestDTO request);


    CrudResponseDTO saveNewHotel(HotelPostRequestDTO request);

    CrudResponseDTO updateHotel(String hotelCode, HotelPostRequestDTO request);

    CrudResponseDTO deleteHotel(String hotelCode);

    CrudResponseDTO updateBooking(Long idBooking, BookingBaseDTO request);

    CrudResponseDTO deleteBooking(Long idBooking);

    List<BookingBaseDTO> getBookings();
}
