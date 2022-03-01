package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.CrudResponseDTO;
import com.example.proyectofinal.dto.hotel.*;
import com.example.proyectofinal.entity.Hotel;
import com.example.proyectofinal.repository.HotelRepository;
import com.example.proyectofinal.util.StringUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;
    private ModelMapper modelMapper = new ModelMapper();

    /**
     * This method gets a list of hotels from the local repository.
     * @return list of hotels.
     */
    @Override
    public List<HotelDTO> getHotels() {
        List<HotelDTO> hotels = new ArrayList<>();
        this.hotelRepository.findAll().forEach(hotel -> hotels.add(
                modelMapper.map(hotel, HotelDTO.class)));
        if (hotels.size() == 0)
            throw new NoSuchElementException("No se encontraron hoteles");
        return hotels;
    }

    /**
     * This method creates a list of HotelDTO containing available hotels.
     * @param request contains dateFrom dateTo and destination parameters.
     * @return list of available hotels.
     */
    @Override
    public List<HotelDTO> getHotelsAvailable(HotelGetRequestDTO request) {
        Assert.isTrue(request.getDateTo().compareTo(request.getDateFrom()) >= 0,
                "La fecha de entrada debe ser menor a la de salida");

        List<HotelDTO> hotels = new ArrayList<>();
        Stream<Hotel> hotelStream = filterHotels(
                request.getDateFrom(),
                request.getDateTo(),
                request.getDestination(),
                false
        );

        hotelStream = hotelStream.filter(hotel -> !hotel.getIsBooking());
        hotelStream.forEach(hotel -> hotels.add(
                modelMapper.map(hotel, HotelDTO.class)));
        return hotels;
    }

    @Override
    public CrudResponseDTO saveNewHotel(HotelDTO request) {
        this.hotelRepository.save(modelMapper.map(request, Hotel.class));
        return new CrudResponseDTO("El proceso terminó satisfactoriamente");
    }

    @Override
    public CrudResponseDTO updateHotel(Integer hotelCode, HotelDTO request) {
        Hotel hotel = this.hotelRepository.findById(hotelCode).get();
        if (request.getName() != null)
            hotel.setName(request.getName());
        if (request.getPlace() != null)
            hotel.setPlace(request.getPlace());
        if (request.getRoomType() != null)
            hotel.setRoomType(request.getRoomType());
        if (request.getRoomPrice() != null)
            hotel.setRoomPrice(request.getRoomPrice());
        if (request.getDisponibilityDateFrom() != null)
            hotel.setDisponibilityDateFrom(request.getDisponibilityDateFrom());
        if (request.getDisponibilityDateTo() != null)
            hotel.setDisponibilityDateTo(request.getDisponibilityDateTo());
        if (request.getIsBooking() != null)
            hotel.setIsBooking(request.getIsBooking());

        this.hotelRepository.save(hotel);
        return new CrudResponseDTO("El proceso terminó satisfactoriamente");
    }

    @Override
    public CrudResponseDTO deleteHotel(Integer hotelCode) {
        this.hotelRepository.deleteById(hotelCode);
        return new CrudResponseDTO("El proceso terminó satisfactoriamente");
    }

    /**
     *
     * This method filters a stream of hotels with the basic request parameters.
     * @param dateFrom date to filter start of reservation.
     * @param dateTo date to filter end of reservation.
     * @param location string to filter hotel location.
     * @param postRequest boolean stating if used in get or post method.
     * @return a stream of hotels.
     */
    private Stream<Hotel> filterHotels(LocalDate dateFrom, LocalDate dateTo, String location, boolean postRequest) {
        Stream<Hotel> hotelStream = this.hotelRepository.findAll().stream();
        if (location != null || postRequest)
            hotelStream = hotelStream.filter(hotel -> StringUtil.normalizeString(hotel.getPlace())
                    .equalsIgnoreCase(StringUtil.normalizeString(location)));

        Assert.isTrue(hotelStream.findAny().isPresent(), "El destino elegido no existe");
        hotelStream = this.hotelRepository.findAll().stream().filter(hotel -> StringUtil.normalizeString(hotel.getPlace())
                .equalsIgnoreCase(StringUtil.normalizeString(location)));

        if (dateFrom != null || postRequest)
            hotelStream = hotelStream.filter(hotel -> hotel.getDisponibilityDateFrom().compareTo(dateFrom) <= 0);
        if (dateTo != null || postRequest)
            hotelStream = hotelStream.filter(hotel -> hotel.getDisponibilityDateTo().compareTo(dateTo) >= 0);
        return hotelStream;
    }
}
