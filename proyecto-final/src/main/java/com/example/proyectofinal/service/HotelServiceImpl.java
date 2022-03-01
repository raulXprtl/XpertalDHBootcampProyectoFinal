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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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

        hotelStream = hotelStream.filter(hotel -> !hotel.getReserved());
        hotelStream.forEach(hotel -> hotels.add(
                modelMapper.map(hotel, HotelDTO.class)));
        return hotels;
    }

    @Override
    public CrudResponseDTO saveNewHotel(HotelPostRequestDTO request) {
        CrudResponseDTO response = new CrudResponseDTO("El proceso terminó satisfactoriamente");

        HotelDTO hotel = new HotelDTO(
                request.getHotelCode(),
                request.getName(),
                request.getPlace(),
                request.getRoomType(),
                request.getRoomPrice(),
                request.getDisponibilityDateFrom(),
                request.getDisponibilityDateTo(),
                request.getIsBooking());

        this.hotelRepository.save(modelMapper.map(hotel, Hotel.class));
        return response;
    }

    @Override
    public CrudResponseDTO updateHotel(String hotelCode, HotelPostRequestDTO request) {
        CrudResponseDTO response = new CrudResponseDTO("El proceso terminó satisfactoriamente");

        Hotel hotel = this.hotelRepository.findById(hotelCode).get();
        if (request.getName() != null)
            hotel.setName(request.getName());
        if (request.getPlace() != null)
            hotel.setLocation(request.getPlace());
        if (request.getRoomPrice() != null)
            hotel.setPricePerNight(request.getRoomPrice());
        if (request.getDisponibilityDateFrom() != null)
            hotel.setDateFrom(request.getDisponibilityDateFrom());
        if (request.getDisponibilityDateTo() != null)
            hotel.setDateTo(request.getDisponibilityDateTo());
        if (request.getIsBooking() != null)
            hotel.setReserved(request.getIsBooking());

        this.hotelRepository.save(hotel);
        return response;
    }

    @Override
    public CrudResponseDTO deleteHotel(String hotelCode) {
        CrudResponseDTO response = new CrudResponseDTO("El proceso terminó satisfactoriamente");
        this.hotelRepository.deleteById(hotelCode);
        return response;
    }

    /**
     * This method returns a reponse for a hotel booking request.
     * @param request contains required request parameters.
     * @return response object.
     */
    @Override
    public CrudResponseDTO postBooking(BookingPostRequestDTO request) {
        CrudResponseDTO response = new CrudResponseDTO("El proceso terminó satisfactoriamente");

        // Night count excludes the end date.
        int nights = ((int) request.getBooking().getDateFrom().until(
                request.getBooking().getDateTo(), ChronoUnit.DAYS));

        // Filters hotels with request parameters.
        Stream<Hotel> hotelStream = filterHotels(
                request.getBooking().getDateFrom(),
                request.getBooking().getDateTo(),
                request.getBooking().getDestination(),
                true);

        if (request.getBooking().getHotelCode() != null)
            hotelStream = hotelStream.filter(hotel -> hotel.getHotelId()
                    .equalsIgnoreCase(request.getBooking().getHotelCode()));
        if (request.getBooking().getRoomType() != null)
            hotelStream = hotelStream.filter(hotel -> hotel.getRoomType()
                    .equalsIgnoreCase(request.getBooking().getRoomType()));

        Optional<Hotel> hotelOptional = hotelStream.findFirst();

        if (hotelOptional.get().getReserved())  // handled by ExceptionHandler
            throw new IllegalArgumentException("Hotel already booked.");

        // Saves new status to DB.
        hotelOptional.get().setReserved(true);
        this.hotelRepository.save(hotelOptional.get());

        return response;
    }

    @Override
    public CrudResponseDTO updateBooking(Long idBooking, BookingBaseDTO request) {

        return new CrudResponseDTO("El proceso terminó satisfactoriamente");
    }

    @Override
    public CrudResponseDTO deleteBooking(Long idBooking) {
        return null;
    }

    @Override
    public List<BookingBaseDTO> getBookings() {
        return null;
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
            hotelStream = hotelStream.filter(hotel -> StringUtil.normalizeString(hotel.getLocation())
                    .equalsIgnoreCase(StringUtil.normalizeString(location)));

        Assert.isTrue(hotelStream.findAny().isPresent(), "El destino elegido no existe");
        hotelStream = this.hotelRepository.findAll().stream().filter(hotel -> StringUtil.normalizeString(hotel.getLocation())
                .equalsIgnoreCase(StringUtil.normalizeString(location)));

        if (dateFrom != null || postRequest)
            hotelStream = hotelStream.filter(hotel -> hotel.getDateFrom().compareTo(dateFrom) <= 0);
        if (dateTo != null || postRequest)
            hotelStream = hotelStream.filter(hotel -> hotel.getDateTo().compareTo(dateTo) >= 0);
        return hotelStream;
    }
}
