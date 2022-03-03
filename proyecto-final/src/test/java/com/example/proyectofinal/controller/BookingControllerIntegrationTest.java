package com.example.proyectofinal.controller;

import com.example.proyectofinal.dto.PaymentMethodDTO;
import com.example.proyectofinal.dto.PersonDTO;
import com.example.proyectofinal.dto.booking.BookingPostRequestDTO;
import com.example.proyectofinal.dto.booking.BookingRequestDTO;
import com.example.proyectofinal.repository.BookingRepository;
import com.example.proyectofinal.repository.HotelRepository;
import com.example.proyectofinal.repository.PersonRepository;
import com.example.proyectofinal.util.UtilDataGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BookingControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    BookingRepository bookingRepository;

    @Test
    @Transactional
    void postBooking() throws Exception {
        Set<PersonDTO> people = UtilDataGenerator.generatePeople(2);

        PaymentMethodDTO paymentMethod = new PaymentMethodDTO(
                "CREDIT", "1234-1234-1234-1234", new BigDecimal(6));

        BookingRequestDTO bookingRequest = new BookingRequestDTO();
        bookingRequest.setDateFrom(LocalDate.of(2022, 5, 1));
        bookingRequest.setDateTo(LocalDate.of(2022, 5, 5));
        bookingRequest.setDestination("Sonora");
        bookingRequest.setHotelCode(5);
        bookingRequest.setPeopleAmount(new BigDecimal(2));
        bookingRequest.setRoomType("Doble");
        bookingRequest.setPeople(people);
        bookingRequest.setPaymentMethod(paymentMethod);

        BookingPostRequestDTO requestDTO = new BookingPostRequestDTO(
                "test@digitalhouse.com", bookingRequest);

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .registerModule(new JavaTimeModule())
                .writer().withDefaultPrettyPrinter();

        String payloadJson = writer.writeValueAsString(requestDTO);

        hotelRepository.updateHotelReservedStatusForId(bookingRequest.getHotelCode());

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/hotel-booking/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadJson))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());
        people.forEach(personDTO -> this.personRepository.deleteByDni(personDTO.getDni()));
    }

    @Test
    void postBookingPaymentTypeDuesError() throws Exception {
        Set<PersonDTO> people = UtilDataGenerator.generatePeople(2);

        PaymentMethodDTO paymentMethod = new PaymentMethodDTO(
                "DEBIT", "1234-1234-1234-1234", new BigDecimal(6));

        BookingRequestDTO bookingRequest = new BookingRequestDTO();
        bookingRequest.setDateFrom(LocalDate.of(2022, 5, 1));
        bookingRequest.setDateTo(LocalDate.of(2022, 5, 5));
        bookingRequest.setDestination("Sonora");
        bookingRequest.setHotelCode(5);
        bookingRequest.setPeopleAmount(new BigDecimal(2));
        bookingRequest.setRoomType("Doble");
        bookingRequest.setPeople(people);
        bookingRequest.setPaymentMethod(paymentMethod);

        BookingPostRequestDTO requestDTO = new BookingPostRequestDTO(
                "test@digitalhouse.com", bookingRequest);

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .registerModule(new JavaTimeModule())
                .writer().withDefaultPrettyPrinter();

        String payloadJson = writer.writeValueAsString(requestDTO);

        hotelRepository.updateHotelReservedStatusForId(bookingRequest.getHotelCode());

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/hotel-booking/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadJson))
                .andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());
        people.forEach(personDTO -> this.personRepository.deleteByDni(personDTO.getDni()));
    }

    @Test
    void postBookingRoomTypePeopleError() throws Exception {
        Set<PersonDTO> people = UtilDataGenerator.generatePeople(2);

        PaymentMethodDTO paymentMethod = new PaymentMethodDTO(
                "CREDIT", "1234-1234-1234-1234", new BigDecimal(6));

        BookingRequestDTO bookingRequest = new BookingRequestDTO();
        bookingRequest.setDateFrom(LocalDate.of(2022, 5, 1));
        bookingRequest.setDateTo(LocalDate.of(2022, 5, 5));
        bookingRequest.setDestination("Sonora");
        bookingRequest.setHotelCode(5);
        bookingRequest.setPeopleAmount(new BigDecimal(2));
        bookingRequest.setRoomType("Triple");
        bookingRequest.setPeople(people);
        bookingRequest.setPaymentMethod(paymentMethod);

        BookingPostRequestDTO requestDTO = new BookingPostRequestDTO(
                "test@digitalhouse.com", bookingRequest);

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .registerModule(new JavaTimeModule())
                .writer().withDefaultPrettyPrinter();

        String payloadJson = writer.writeValueAsString(requestDTO);

        hotelRepository.updateHotelReservedStatusForId(bookingRequest.getHotelCode());

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/hotel-booking/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadJson))
                .andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());
        people.forEach(personDTO -> this.personRepository.deleteByDni(personDTO.getDni()));
    }
}