package com.example.proyectofinal.util;

import com.example.proyectofinal.dto.PaymentMethodDTO;
import com.example.proyectofinal.dto.PersonDTO;
import com.example.proyectofinal.dto.StatusCodeDTO;
import com.example.proyectofinal.dto.booking.BookingBaseDTO;
import com.example.proyectofinal.dto.booking.BookingPostRequestDTO;
import com.example.proyectofinal.dto.booking.BookingPostResponseDTO;
import com.example.proyectofinal.dto.booking.BookingRequestDTO;
import com.example.proyectofinal.dto.hotel.*;
import com.example.proyectofinal.entity.Hotel;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HotelDataGenerator {
    public static Hotel getHotelTest(String name, String destination){
        ModelMapper modelMapper = new ModelMapper();
        HotelDTO hotel = new HotelDTO(null, name, destination,
                "Single", 6300.0,
                LocalDate.of(2022, 2, 10),
                LocalDate.of(2022, 3, 20),
                false);
        return modelMapper.map(hotel, Hotel.class);
    }

    public static List<Hotel> getHotelList(int count) {
        List<Hotel> hotelList = new ArrayList<>();
        for (int ii = 0; ii < count; ii++) {
            hotelList.add(getHotelTest(
                    String.format("Test Hotel %d", ii),
                    String.format("Test Dest. %d", ii)));
        }
        return hotelList;
    }

    public static List<HotelDTO> getHotelDTOList(List<Hotel> hotels) {
        List<HotelDTO> hotelList = new ArrayList<>();
        hotels.forEach(hotel -> hotelList.add(new HotelDTO(
                hotel.getHotelCode(),
                hotel.getName(),
                hotel.getPlace(),
                hotel.getRoomType(),
                hotel.getRoomPrice(),
                hotel.getDisponibilityDateFrom(),
                hotel.getDisponibilityDateTo(),
                hotel.getIsBooking())));
        return hotelList;
    }

    private static BookingBaseDTO getBookingBase(HotelDTO hotel,
                                                 BigDecimal peopleAmount,
                                                 Set<PersonDTO> people) {
        return new BookingBaseDTO(
                null,
                hotel.getDisponibilityDateFrom(),
                hotel.getDisponibilityDateTo(),
                hotel.getPlace(),
                hotel.getHotelCode(),
                peopleAmount,
                hotel.getRoomType(),
                people);
    }

    private static BookingRequestDTO getBookingRequest(HotelDTO hotel,
                                                       BigDecimal peopleAmount,
                                                       Set<PersonDTO> people,
                                                       PaymentMethodDTO paymentMethod) {
        BookingBaseDTO booking = getBookingBase(hotel, peopleAmount, people);
        return new BookingRequestDTO(booking, paymentMethod);
    }

    private static BigDecimal getPeopleAmount(String roomType) {
        switch (roomType) {
            case "Single": return new BigDecimal(1);
            case "Doble": return new BigDecimal(2);
            case "Triple": return new BigDecimal(3);
            default: return new BigDecimal(4);
        }
    }

    public static BookingPostRequestDTO getHotelPostRequest(HotelDTO hotel, String paymentType) {
        BigDecimal peopleAmount = getPeopleAmount(hotel.getRoomType());

        Set<PersonDTO> people = new HashSet<>();
        for (int ii = 0; ii < peopleAmount.intValue(); ii++)
            people.add(new PersonDTO());

        PaymentMethodDTO paymentMethod;
        if (paymentType.equalsIgnoreCase("DEBIT")) {
            paymentMethod = new PaymentMethodDTO(
                    paymentType,
                    "1234-1234-1234-1234",
                    BigDecimal.ONE);
        } else {
            paymentMethod = new PaymentMethodDTO(
                    "CREDIT",
                    "1234-1234-1234-1234",
                    new BigDecimal(3));
        }

        BookingPostRequestDTO request = new BookingPostRequestDTO();
        request.setUserName("test@digitalhouse.com");
        request.setBooking(getBookingRequest(hotel, peopleAmount, people, paymentMethod));

        return request;
    }
}
