package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.StatusCodeDTO;
import com.example.proyectofinal.dto.hotel.HotelDTO;
import com.example.proyectofinal.dto.hotel.HotelGetRequestDTO;
import com.example.proyectofinal.dto.hotel.HotelPostRequestDTO;
import com.example.proyectofinal.dto.hotel.HotelPostResponseDTO;
import com.example.proyectofinal.entity.Hotel;
import com.example.proyectofinal.repository.HotelRepository;
import com.example.proyectofinal.util.StringUtil;
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

    /**
     * This method gets a list of hotels from the local repository.
     * @return list of hotels.
     */
    @Override
    public List<HotelDTO> getHotels() {
        List<HotelDTO> hotelList = new ArrayList<>();
        this.hotelRepository.findAll().forEach(hotel -> hotelList.add(new HotelDTO(
                hotel.getHotelCode(),
                hotel.getName(),
                hotel.getLocation(),
                hotel.getRoomType(),
                hotel.getPricePerNight(),
                hotel.getDateFrom(),
                hotel.getDateTo(),
                hotel.getReserved())));
        if (hotelList.size() == 0)
            throw new NoSuchElementException("No se encontraron hoteles");
        return hotelList;
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

        List<HotelDTO> hotelList = new ArrayList<>();
        Stream<Hotel> hotelStream = filterHotels(
                request.getDateFrom(),
                request.getDateTo(),
                request.getDestination(),
                false
        );

        hotelStream = hotelStream.filter(hotel -> !hotel.getReserved());
        hotelStream.forEach(hotel -> hotelList.add(new HotelDTO(
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

    /**
     * This method returns a reponse for a hotel booking request.
     * @param request contains required request parameters.
     * @return response object.
     */
    @Override
    public HotelPostResponseDTO postBooking(HotelPostRequestDTO request) {
        HotelPostResponseDTO response = new HotelPostResponseDTO();

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
            hotelStream = hotelStream.filter(hotel -> hotel.getHotelCode()
                    .equalsIgnoreCase(request.getBooking().getHotelCode()));
        if (request.getBooking().getRoomType() != null)
            hotelStream = hotelStream.filter(hotel -> hotel.getRoomType()
                    .equalsIgnoreCase(request.getBooking().getRoomType()));

        Optional<Hotel> hotelOptional = hotelStream.findFirst();

        if (hotelOptional.get().getReserved())  // handled by ExceptionHandler
            throw new IllegalArgumentException("Hotel already booked.");

        // Sets response values
        response.setUserName(request.getUserName());
        response.setAmount(hotelOptional.get().getPricePerNight().doubleValue() * nights);
        response.setInterest(request.getBooking().getPaymentMethod().calculateInterest());
        response.setTotal(response.getAmount() * (1 + response.getInterest() / 100));
        response.setBooking(request.getBooking());
        response.setStatusCode(
                new StatusCodeDTO(200, "El proceso terminó satisfactoriamente"));

        // Saves new status to DB.
        hotelOptional.get().setReserved(true);
        hotelRepository.save(hotelOptional.get());

        return response;
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
