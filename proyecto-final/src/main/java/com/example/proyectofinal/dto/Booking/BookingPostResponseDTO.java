package com.example.proyectofinal.dto.Booking;

import com.example.proyectofinal.dto.Booking.BookingBaseDTO;
import com.example.proyectofinal.dto.Booking.BookingRequestDTO;
import com.example.proyectofinal.dto.StatusCodeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingPostResponseDTO {
    private String userName;
    private Double amount;
    private Double interest;
    private Double total;
    private BookingBaseDTO booking;
    private StatusCodeDTO statusCode;

    public void setBooking(BookingRequestDTO booking) {
        this.booking = new BookingBaseDTO(
                booking.getBookingId(),
                booking.getDateFrom(),
                booking.getDateTo(),
                booking.getDestination(),
                booking.getHotelCode(),
                booking.getPeopleAmount(),
                booking.getRoomType(),
                booking.getPeople());
    }
}
