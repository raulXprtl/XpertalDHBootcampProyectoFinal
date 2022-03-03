package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.hotel.HotelDTO;
import com.example.proyectofinal.dto.hotel.HotelGetRequestDTO;
import com.example.proyectofinal.entity.Hotel;
import com.example.proyectofinal.repository.HotelRepository;
import com.example.proyectofinal.util.HotelDataGenerator;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
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
                hotels.get(0).getPlace(),
                hotels.get(0).getRoomType(),
                hotels.get(0).getRoomPrice(),
                hotels.get(0).getDisponibilityDateFrom(),
                hotels.get(0).getDisponibilityDateTo(),
                hotels.get(0).getIsBooking()));

        when(repo.findAll()).thenReturn(hotels);

        HotelGetRequestDTO request = new HotelGetRequestDTO(
                hotels.get(0).getDisponibilityDateFrom(),
                hotels.get(0).getDisponibilityDateTo(),
                hotels.get(0).getPlace());

        List<HotelDTO> readHotelsAvailable = service.getHotelsAvailable(request);

        verify(repo, atLeastOnce()).findAll();
        assertTrue(CollectionUtils.isEqualCollection(hotelListAvailable, readHotelsAvailable));
    }

    @Test
    void getHotelsAvailableDestinationException() {
        List<Hotel> hotels = HotelDataGenerator.getHotelList(4);

        HotelGetRequestDTO request = new HotelGetRequestDTO(
                hotels.get(0).getDisponibilityDateFrom(),
                hotels.get(0).getDisponibilityDateTo(),
                "Bad Destination");

        when(repo.findAll()).thenReturn(hotels);

        assertThrows(IllegalArgumentException.class, () -> service.getHotelsAvailable(request));
        verify(repo, atLeastOnce()).findAll();
    }

    @Test
    void getHotelsAvailableDateMatchException() {
        List<Hotel> hotels = HotelDataGenerator.getHotelList(4);

        HotelGetRequestDTO request = new HotelGetRequestDTO(
                hotels.get(0).getDisponibilityDateFrom(),
                hotels.get(0).getDisponibilityDateTo(),
                hotels.get(0).getPlace());

        assertThrows(IllegalArgumentException.class, () -> service.getHotelsAvailable(request));
    }
}
