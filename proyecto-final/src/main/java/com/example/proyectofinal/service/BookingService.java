package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.booking.BookingRequestDTO;
import com.example.proyectofinal.dto.CrudResponseDTO;
import com.example.proyectofinal.dto.booking.BookingPostRequestDTO;

import java.util.List;

public interface BookingService {
    CrudResponseDTO postBooking(BookingPostRequestDTO request);

    CrudResponseDTO updateBooking(Integer idBooking, BookingPostRequestDTO request);

    CrudResponseDTO deleteBooking(Integer idBooking);

    List<BookingRequestDTO> getBookings();
}
