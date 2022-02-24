package com.example.proyectofinal.controller;

import com.example.proyectofinal.dto.PaymentMethodDTO;
import com.example.proyectofinal.dto.PersonDTO;
import com.example.proyectofinal.dto.flight.FlightPostRequestDTO;
import com.example.proyectofinal.dto.flight.FlightReservationRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FlightControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getFlights() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/flights"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());
    }

    @Test
    void getAvailableFlights() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/flights")
                        .param("dateFrom", "10/02/2022")
                        .param("dateTo", "15/02/2022")
                        .param("origin", "Buenos Aires")
                        .param("destination", "Puerto Iguazú"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());
    }

    @Test
    void getAvailableFlightsNoOriginError() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/flights")
                        .param("dateFrom", "10/02/2022")
                        .param("dateTo", "15/02/2022")
                        .param("destination", "Puerto Iguazú"))
                .andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());
    }

    @Test
    void getAvailableFlightsNoDestinationError() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/flights")
                        .param("dateFrom", "10/02/2022")
                        .param("dateTo", "15/02/2022")
                        .param("origin", "Buenos Aires"))
                .andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());
    }

    @Test
    void getAvailableFlightsOriginNotFoundError() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/flights")
                        .param("dateFrom", "10/02/2022")
                        .param("dateTo", "15/02/2022")
                        .param("origin", "Monterrey")
                        .param("destination", "Puerto Iguazú"))
                .andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());
    }

    @Test
    void getAvailableFlightsDestinationNotFoundError() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/flights")
                        .param("dateFrom", "10/02/2022")
                        .param("dateTo", "15/02/2022")
                        .param("origin", "Buenos Aires")
                        .param("destination", "Monterrey"))
                .andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());
    }

    @Test
    void getAvailableFlightsDateFormatError() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/flights")
                        .param("dateFrom", "10/2/2022")
                        .param("dateTo", "15/02/2022")
                        .param("origin", "Buenos Aires")
                        .param("destination", "Puerto Iguazú"))
                .andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());
    }

    @Test
    void getAvailableFlightsDateChronologicalError() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/flights")
                        .param("dateFrom", "15/02/2022")
                        .param("dateTo", "10/02/2022")
                        .param("origin", "Buenos Aires")
                        .param("destination", "Puerto Iguazú"))
                .andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());
    }

    @Test
    void postFlightReservation() throws Exception {
        List<PersonDTO> people = new ArrayList<>();
        for (int ii = 0; ii < 2; ii++) {
            people.add(new PersonDTO());
        }

        PaymentMethodDTO paymentMethod = new PaymentMethodDTO(
                "CREDIT", "1234-1234-1234-1234", new BigDecimal(6));

        FlightReservationRequestDTO reservationRequest = new FlightReservationRequestDTO();
        reservationRequest.setDateFrom(LocalDate.of(2022, 2, 10));
        reservationRequest.setDateTo(LocalDate.of(2022, 2, 15));
        reservationRequest.setOrigin("Buenos Aires");
        reservationRequest.setDestination("Puerto Iguazú");
        reservationRequest.setFlightNumber("BAPI-1235");
        reservationRequest.setSeats(new BigDecimal(2));
        reservationRequest.setSeatType("Economy");
        reservationRequest.setPeople(people);
        reservationRequest.setPaymentMethod(paymentMethod);

        FlightPostRequestDTO requestDTO = new FlightPostRequestDTO(
                "test@digitalhouse.com", reservationRequest);

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .registerModule(new JavaTimeModule())
                .writer().withDefaultPrettyPrinter();

        String payloadJson = writer.writeValueAsString(requestDTO);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/flight-reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadJson))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());
    }

    @Test
    void postFlightReservationPaymentTypeDuesError() throws Exception {
        List<PersonDTO> people = new ArrayList<>();
        for (int ii = 0; ii < 2; ii++) {
            people.add(new PersonDTO());
        }

        PaymentMethodDTO paymentMethod = new PaymentMethodDTO(
                "DEBIT", "1234-1234-1234-1234", new BigDecimal(6));

        FlightReservationRequestDTO reservationRequest = new FlightReservationRequestDTO();
        reservationRequest.setDateFrom(LocalDate.of(2022, 2, 10));
        reservationRequest.setDateTo(LocalDate.of(2022, 2, 15));
        reservationRequest.setOrigin("Buenos Aires");
        reservationRequest.setDestination("Puerto Iguazú");
        reservationRequest.setFlightNumber("BAPI-1235");
        reservationRequest.setSeats(new BigDecimal(2));
        reservationRequest.setSeatType("Economy");
        reservationRequest.setPeople(people);
        reservationRequest.setPaymentMethod(paymentMethod);

        FlightPostRequestDTO requestDTO = new FlightPostRequestDTO(
                "test@digitalhouse.com", reservationRequest);

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .registerModule(new JavaTimeModule())
                .writer().withDefaultPrettyPrinter();

        String payloadJson = writer.writeValueAsString(requestDTO);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/flight-reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadJson))
                .andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());
    }
}