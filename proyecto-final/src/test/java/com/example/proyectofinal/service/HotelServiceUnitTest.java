package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.hotel.HotelDTO;
import com.example.proyectofinal.dto.hotel.HotelGetRequestDTO;
import com.example.proyectofinal.dto.hotel.HotelPostResponseDTO;
import com.example.proyectofinal.entity.Hotel;
import com.example.proyectofinal.repository.HotelRepository;
import com.example.proyectofinal.util.HotelDataGenerator;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelServiceUnitTest {
    @Mock
    HotelRepository repo;

    @InjectMocks
    HotelServiceImpl service;

    @Test
    void getHotels() {
        List<Hotel> hotels = HotelDataGenerator.getHotelList(4);
        List<HotelDTO> hotelList = HotelDataGenerator.getHotelDTOList(hotels);

        when(repo.findAll()).thenReturn(hotels);

        List<HotelDTO> readHotels = service.getHotels();

        verify(repo, atLeastOnce()).findAll();
        assertTrue(CollectionUtils.isEqualCollection(hotelList, readHotels));
    }

    @Test
    void getHotelsEmpty() {
        List<Hotel> hotels = new ArrayList<>();

        when(repo.findAll()).thenReturn(hotels);

        assertThrows(NoSuchElementException.class, () -> service.getHotels());
        verify(repo, atLeastOnce()).findAll();
    }

    @Test
    void getHotelsAvailable() {
        List<Hotel> hotels = HotelDataGenerator.getHotelList(4);
        List<HotelDTO> hotelListAvailable = new ArrayList<>();
        hotelListAvailable.add(new HotelDTO(
                hotels.get(0).getHotelCode(),
                hotels.get(0).getName(),
                hotels.get(0).getLocation(),
                hotels.get(0).getRoomType(),
                hotels.get(0).getPricePerNight(),
                hotels.get(0).getDateFrom(),
                hotels.get(0).getDateTo(),
                hotels.get(0).getReserved()));

        when(repo.findAll()).thenReturn(hotels);

        HotelGetRequestDTO request = new HotelGetRequestDTO(
                hotels.get(0).getDateFrom(),
                hotels.get(0).getDateTo(),
                hotels.get(0).getLocation());

        List<HotelDTO> readHotelsAvailable = service.getHotelsAvailable(request);

        verify(repo, atLeastOnce()).findAll();
        assertTrue(CollectionUtils.isEqualCollection(hotelListAvailable, readHotelsAvailable));
    }

    @Test
    void getHotelsAvailableDestinationException() {
        List<Hotel> hotels = HotelDataGenerator.getHotelList(4);

        HotelGetRequestDTO request = new HotelGetRequestDTO(
                hotels.get(0).getDateFrom(),
                hotels.get(0).getDateTo(),
                "Bad Destination");

        when(repo.findAll()).thenReturn(hotels);

        assertThrows(IllegalArgumentException.class, () -> service.getHotelsAvailable(request));
        verify(repo, atLeastOnce()).findAll();
    }

    @Test
    void getHotelsAvailableDateMatchException() {
        List<Hotel> hotels = HotelDataGenerator.getHotelList(4);

        HotelGetRequestDTO request = new HotelGetRequestDTO(
                hotels.get(0).getDateFrom(),
                hotels.get(0).getDateTo(),
                hotels.get(0).getLocation());

        assertThrows(IllegalArgumentException.class, () -> service.getHotelsAvailable(request));
    }

    @Test
    void postBooking() {
        List<Hotel> hotels = HotelDataGenerator.getHotelList(4);
        List<HotelDTO> hotelList = HotelDataGenerator.getHotelDTOList(hotels);

        HotelPostResponseDTO expectedResponse = HotelDataGenerator.getHotelPostResponse(
                hotelList.get(0),
                HotelDataGenerator.calculateHotelTotalCost(hotelList.get(0)),
                0.0);

        when(repo.findAll()).thenReturn(hotels);

        HotelPostResponseDTO response = service.postBooking(
                HotelDataGenerator.getHotelPostRequest(hotelList.get(0), "DEBIT"));

        verify(repo, atLeastOnce()).findAll();
        assertEquals(expectedResponse, response);
    }

    @Test
    void postBookingDestinationException() {
        List<Hotel> hotels = HotelDataGenerator.getHotelList(4);

        when(repo.findAll()).thenReturn(hotels);

        HotelDTO hotelRequest = new HotelDTO(
                hotels.get(0).getHotelCode(),
                hotels.get(0).getName(),
                "Bad Destination",
                hotels.get(0).getRoomType(),
                hotels.get(0).getPricePerNight(),
                hotels.get(0).getDateFrom(),
                hotels.get(0).getDateTo(),
                hotels.get(0).getReserved());

        assertThrows(IllegalArgumentException.class, () -> service.postBooking(
                HotelDataGenerator.getHotelPostRequest(hotelRequest, "DEBIT")));
        verify(repo, atLeastOnce()).findAll();
    }

    @Test
    void postBookingDateOutOfRangeException() {
        List<Hotel> hotels = HotelDataGenerator.getHotelList(4);

        when(repo.findAll()).thenReturn(hotels);

        HotelDTO hotelRequest = new HotelDTO(
                hotels.get(0).getHotelCode(),
                hotels.get(0).getName(),
                hotels.get(0).getLocation(),
                hotels.get(0).getRoomType(),
                hotels.get(0).getPricePerNight(),
                hotels.get(0).getDateFrom(),
                hotels.get(0).getDateTo().plus(1, ChronoUnit.DAYS),
                hotels.get(0).getReserved());

        assertThrows(NoSuchElementException.class, () -> service.postBooking(
                HotelDataGenerator.getHotelPostRequest(hotelRequest, "DEBIT")));
        verify(repo, atLeastOnce()).findAll();
    }
}
