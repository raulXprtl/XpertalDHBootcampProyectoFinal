package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.CrudResponseDTO;
import com.example.proyectofinal.dto.hotel.*;

import java.util.List;

public interface HotelService {
    List<HotelDTO> getHotels();

    List<HotelDTO> getHotelsAvailable(HotelGetRequestDTO request);

    CrudResponseDTO postBooking(HotelPostRequestDTO request);


    CrudResponseDTO saveNewHotel(HotelNewPostRequestDTO request);

    CrudResponseDTO updateHotel(String hotelCode, HotelNewPostRequestDTO request);

    CrudResponseDTO deleteHotel(String hotelCode);

    CrudResponseDTO updateBooking(Long idBooking, BookingUpdateDTO request);

    CrudResponseDTO deleteBooking(Long idBooking);

    List<BookingDTO> getBookings();
}
