package com.example.proyectofinal.service;

import com.example.proyectofinal.dto.Booking.BookingRequestDTO;
import com.example.proyectofinal.dto.CrudResponseDTO;
import com.example.proyectofinal.dto.Booking.BookingPostRequestDTO;
import com.example.proyectofinal.entity.Booking;
import com.example.proyectofinal.entity.Hotel;
import com.example.proyectofinal.entity.PaymentMethod;
import com.example.proyectofinal.entity.Person;
import com.example.proyectofinal.mapping.dto_entity.BookingMap;
import com.example.proyectofinal.mapping.entity_dto.BookingDTOMap;
import com.example.proyectofinal.repository.BookingRepository;
import com.example.proyectofinal.repository.HotelRepository;
import com.example.proyectofinal.repository.PersonRepository;
import com.example.proyectofinal.util.StringUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Stream;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    BookingRepository bookingRepository;

    /**
     * This method returns a reponse for a hotel booking request.
     * @param request contains required request parameters.
     * @return response object.
     */
    @Override
    public CrudResponseDTO postBooking(BookingPostRequestDTO request) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new BookingMap());

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
                    .equals(request.getBooking().getHotelCode()));
        if (request.getBooking().getRoomType() != null)
            hotelStream = hotelStream.filter(hotel -> hotel.getRoomType()
                    .equalsIgnoreCase(request.getBooking().getRoomType()));

        Optional<Hotel> hotelOptional = hotelStream.findFirst();

        if (hotelOptional.get().getIsBooking())  // handled by ExceptionHandler
            throw new IllegalArgumentException("Hotel already booked.");

        // Saves new status to Hotel table.
        hotelOptional.get().setIsBooking(true);
        this.hotelRepository.save(hotelOptional.get());

        // Saves new item to Booking table.
        Booking booking = modelMapper.map(request.getBooking(), Booking.class);
        booking.setHotel(hotelOptional.get());
        booking.getPeople().forEach(person -> this.personRepository.save(person));
        this.bookingRepository.save(booking);

        return new CrudResponseDTO("El proceso terminó satisfactoriamente");
    }

    @Override
    public CrudResponseDTO updateBooking(Integer idBooking, BookingPostRequestDTO request) {
        ModelMapper modelMapper = new ModelMapper();
        Booking booking = this.bookingRepository.getById(idBooking);
        if (request.getBooking().getDateFrom() != null)
            booking.setDateFrom(request.getBooking().getDateFrom());
        if (request.getBooking().getDateTo() != null)
            booking.setDateTo(request.getBooking().getDateTo());
        if (request.getBooking().getHotelCode() != null)
            booking.setHotel(this.hotelRepository.getById(request.getBooking().getHotelCode()));
        if (request.getBooking().getPaymentMethod() != null) {
            booking.setPaymentMethod(modelMapper.map(
                    request.getBooking().getPaymentMethod(), PaymentMethod.class));
        }
        if (request.getBooking().getPeople() != null) {
            Set<Person> people = new HashSet<>();
            request.getBooking().getPeople().forEach(person -> people.add(
                    modelMapper.map(person, Person.class)));
            people.forEach(person -> this.personRepository.save(person));
            booking.setPeople(people);
        }
        this.bookingRepository.save(booking);
        return new CrudResponseDTO("El proceso terminó satisfactoriamente");
    }

    @Override
    public CrudResponseDTO deleteBooking(Integer idBooking) {
        this.bookingRepository.deleteById(idBooking);
        return new CrudResponseDTO("El proceso terminó satisfactoriamente");
    }

    @Override
    public List<BookingRequestDTO> getBookings() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new BookingDTOMap());

        List<BookingRequestDTO> requests = new ArrayList<>();

        List<Booking> bookings = this.bookingRepository.findAll();
        for (Booking booking : bookings) {
            BookingRequestDTO request = modelMapper.map(booking, BookingRequestDTO.class);
            request.setDestination(booking.getHotel().getPlace());
            request.setHotelCode(booking.getHotel().getHotelCode());
            request.setPeopleAmount(new BigDecimal(booking.getPeople().size()));
            request.setRoomType(booking.getHotel().getRoomType());
            requests.add(request);
        }
        if (requests.size() == 0)
            throw new NoSuchElementException("No se encontraron hoteles");
        return requests;
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
