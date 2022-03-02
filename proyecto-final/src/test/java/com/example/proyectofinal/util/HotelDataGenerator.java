package com.example.proyectofinal.util;

import com.example.proyectofinal.dto.PaymentMethodDTO;
import com.example.proyectofinal.dto.PersonDTO;
import com.example.proyectofinal.dto.StatusCodeDTO;
import com.example.proyectofinal.dto.hotel.*;
import com.example.proyectofinal.entity.Hotel;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class HotelDataGenerator {
    public static Hotel getHotelTest(int code, String name, String destination){
        return new Hotel(String.format("TH-%03d", code),
                name,
                destination,
                "Single",
                6300,
                LocalDate.of(2022, 2, 10),
                LocalDate.of(2022, 3, 20),
                false);
    }

    public static List<Hotel> getHotelList(int count) {
        List<Hotel> hotelList = new ArrayList<>();
        for (int ii = 0; ii < count; ii++) {
            hotelList.add(getHotelTest(
                    ii,
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
                hotel.getLocation(),
                hotel.getRoomType(),
                hotel.getPricePerNight(),
                hotel.getDateFrom(),
                hotel.getDateTo(),
                hotel.getReserved())));
        return hotelList;
    }

    public static double calculateHotelTotalCost(HotelDTO hotel) {
        int nights = ((int) LocalDate.of(2022, 2, 10).until(
                LocalDate.of(2022, 3, 20),
                ChronoUnit.DAYS));
        return hotel.getPricePerNight() * nights;
    }

    private static BookingBaseDTO getBookingBase(HotelDTO hotel,
                                          BigDecimal peopleAmount,
                                          List<PersonDTO> people) {
        return new BookingBaseDTO(
                hotel.getDateFrom(),
                hotel.getDateTo(),
                hotel.getLocation(),
                hotel.getHotelId(),
                peopleAmount,
                hotel.getRoomType(),
                people);
    }

    private static BookingRequestDTO getBookingRequest(HotelDTO hotel,
                                                       BigDecimal peopleAmount,
                                                       List<PersonDTO> people,
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

    public static HotelPostResponseDTO getHotelPostResponse(HotelDTO hotel, double totalCost, double interest){
        BigDecimal peopleAmount = getPeopleAmount(hotel.getRoomType());

        List<PersonDTO> people = new ArrayList<>();
        for (int ii = 0; ii < peopleAmount.intValue(); ii++)
            people.add(new PersonDTO());

        return new HotelPostResponseDTO(
                "test@digitalhouse.com",
                totalCost,
                interest,
                totalCost * (1 + interest / 100),
                getBookingBase(hotel,peopleAmount, people),
                new StatusCodeDTO(HttpStatus.OK.value(),
                        "El proceso terminó satisfactoriamente"));
    }

    public static HotelPostRequestDTO getHotelPostRequest(HotelDTO hotel, String paymentType) {
        BigDecimal peopleAmount = getPeopleAmount(hotel.getRoomType());

        List<PersonDTO> people = new ArrayList<>();
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

        HotelPostRequestDTO request = new HotelPostRequestDTO();
        request.setUserName("test@digitalhouse.com");
        request.setBooking(getBookingRequest(hotel, peopleAmount, people, paymentMethod));

        return request;
    }
}
