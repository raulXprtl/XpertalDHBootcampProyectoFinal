package com.example.proyectofinal.controller;

import com.example.proyectofinal.dto.PaymentMethodDTO;
import com.example.proyectofinal.dto.PersonDTO;
import com.example.proyectofinal.dto.reservation.ReservationPostRequestDTO;
import com.example.proyectofinal.dto.reservation.ReservationRequestDTO;
import com.example.proyectofinal.repository.PersonRepository;
import com.example.proyectofinal.repository.ReservationRepository;
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
class ReservationControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonRepository personRepository;
    @Autowired
    ReservationRepository reservationRepository;

    @Test
    @Transactional
    void postFlightReservation() throws Exception {
        Set<PersonDTO> people = UtilDataGenerator.generatePeople(2);

        PaymentMethodDTO paymentMethod = new PaymentMethodDTO(
                "CREDIT", "1234-1234-1234-1234", new BigDecimal(6));

        ReservationRequestDTO reservationRequest = new ReservationRequestDTO();
        reservationRequest.setGoingDate(LocalDate.of(2022, 8, 1));
        reservationRequest.setReturnDate(LocalDate.of(2022, 8, 9));
        reservationRequest.setOrigin("Monterrey");
        reservationRequest.setDestination("Tamaulipas");
        reservationRequest.setFlightNumber(6);
        reservationRequest.setSeats(new BigDecimal(2));
        reservationRequest.setSeatType("Confort");
        reservationRequest.setPeople(people);
        reservationRequest.setPaymentMethod(paymentMethod);

        ReservationPostRequestDTO requestDTO = new ReservationPostRequestDTO(
                "test@digitalhouse.com", reservationRequest);

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .registerModule(new JavaTimeModule())
                .writer().withDefaultPrettyPrinter();

        String payloadJson = writer.writeValueAsString(requestDTO);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/flight-reservation/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadJson))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());

        people.forEach(personDTO -> this.personRepository.deleteByDni(personDTO.getDni()));
    }

    @Test
    void postFlightReservationPaymentTypeDuesError() throws Exception {
        Set<PersonDTO> people = UtilDataGenerator.generatePeople(2);

        PaymentMethodDTO paymentMethod = new PaymentMethodDTO(
                "DEBIT", "1234-1234-1234-1234", new BigDecimal(6));

        ReservationRequestDTO reservationRequest = new ReservationRequestDTO();
        reservationRequest.setGoingDate(LocalDate.of(2022, 8, 1));
        reservationRequest.setReturnDate(LocalDate.of(2022, 8, 9));
        reservationRequest.setOrigin("Monterrey");
        reservationRequest.setDestination("Tamaulipas");
        reservationRequest.setFlightNumber(6);
        reservationRequest.setSeats(new BigDecimal(2));
        reservationRequest.setSeatType("Confort");
        reservationRequest.setPeople(people);
        reservationRequest.setPaymentMethod(paymentMethod);

        ReservationPostRequestDTO requestDTO = new ReservationPostRequestDTO(
                "test@digitalhouse.com", reservationRequest);

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .registerModule(new JavaTimeModule())
                .writer().withDefaultPrettyPrinter();

        String payloadJson = writer.writeValueAsString(requestDTO);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/flight-reservation/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadJson))
                .andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());
    }
}