package com.example.proyectofinal.controller;

import com.example.proyectofinal.dto.PaymentMethodDTO;
import com.example.proyectofinal.dto.PersonDTO;
import com.example.proyectofinal.dto.hotel.BookingRequestDTO;
import com.example.proyectofinal.dto.hotel.HotelPostRequestDTO;
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
class HotelControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getHotels() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/hotels"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());
    }

    @Test
    void getAvailableHotels() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/hotels")
                        .param("dateFrom", "10/02/2022")
                        .param("dateTo", "20/03/2022")
                        .param("destination", "Puerto Iguazú"))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());
    }

    @Test
    void getAvailableHotelsNoDestinationError() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/hotels")
                        .param("dateFrom", "10/02/2022")
                        .param("dateTo", "20/03/2022"))
                .andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());
    }

    @Test
    void getAvailableHotelsDestinationNotFoundError() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/hotels")
                        .param("dateFrom", "10/02/2022")
                        .param("dateTo", "20/03/2022")
                        .param("destination", "Monterrey"))
                .andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());
    }

    @Test
    void getAvailableHotelsDateFormatError() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/hotels")
                        .param("dateFrom", "10/2/2022")
                        .param("dateTo", "20/03/2022")
                        .param("destination", "Puerto Iguazú"))
                .andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());
    }

    @Test
    void getAvailableHotelsDateChronologicalError() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/hotels")
                        .param("dateFrom", "20/03/2022")
                        .param("dateTo", "10/02/2022")
                        .param("destination", "Puerto Iguazú"))
                .andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());
    }

    @Test
    void postBooking() throws Exception {
        List<PersonDTO> people = new ArrayList<>();
        for (int ii = 0; ii < 2; ii++) {
            people.add(new PersonDTO());
        }

        PaymentMethodDTO paymentMethod = new PaymentMethodDTO(
                "CREDIT", "1234-1234-1234-1234", new BigDecimal(6));

        BookingRequestDTO bookingRequest = new BookingRequestDTO();
        bookingRequest.setDateFrom(LocalDate.of(2022, 2, 10));
        bookingRequest.setDateTo(LocalDate.of(2022, 3, 20));
        bookingRequest.setDestination("Puerto Iguazú");
        bookingRequest.setHotelCode("CH-0002");
        bookingRequest.setPeopleAmount(new BigDecimal(2));
        bookingRequest.setRoomType("Doble");
        bookingRequest.setPeople(people);
        bookingRequest.setPaymentMethod(paymentMethod);

        HotelPostRequestDTO requestDTO = new HotelPostRequestDTO(
                "test@digitalhouse.com", bookingRequest);

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .registerModule(new JavaTimeModule())
                .writer().withDefaultPrettyPrinter();

        String payloadJson = writer.writeValueAsString(requestDTO);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadJson))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());
    }

    @Test
    void postBookingPaymentTypeDuesError() throws Exception {
        List<PersonDTO> people = new ArrayList<>();
        for (int ii = 0; ii < 2; ii++) {
            people.add(new PersonDTO());
        }

        PaymentMethodDTO paymentMethod = new PaymentMethodDTO(
                "DEBIT", "1234-1234-1234-1234", new BigDecimal(6));

        BookingRequestDTO bookingRequest = new BookingRequestDTO();
        bookingRequest.setDateFrom(LocalDate.of(2022, 2, 10));
        bookingRequest.setDateTo(LocalDate.of(2022, 3, 20));
        bookingRequest.setDestination("Puerto Iguazú");
        bookingRequest.setHotelCode("CH-0002");
        bookingRequest.setPeopleAmount(new BigDecimal(2));
        bookingRequest.setRoomType("Doble");
        bookingRequest.setPeople(people);
        bookingRequest.setPaymentMethod(paymentMethod);

        HotelPostRequestDTO requestDTO = new HotelPostRequestDTO(
                "test@digitalhouse.com", bookingRequest);

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .registerModule(new JavaTimeModule())
                .writer().withDefaultPrettyPrinter();

        String payloadJson = writer.writeValueAsString(requestDTO);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadJson))
                .andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());
    }

    @Test
    void postBookingRoomTypePeopleError() throws Exception {
        List<PersonDTO> people = new ArrayList<>();
        for (int ii = 0; ii < 2; ii++) {
            people.add(new PersonDTO());
        }

        PaymentMethodDTO paymentMethod = new PaymentMethodDTO(
                "CREDIT", "1234-1234-1234-1234", new BigDecimal(6));

        BookingRequestDTO bookingRequest = new BookingRequestDTO();
        bookingRequest.setDateFrom(LocalDate.of(2022, 2, 10));
        bookingRequest.setDateTo(LocalDate.of(2022, 3, 20));
        bookingRequest.setDestination("Puerto Iguazú");
        bookingRequest.setHotelCode("CH-0002");
        bookingRequest.setPeopleAmount(new BigDecimal(2));
        bookingRequest.setRoomType("Triple");
        bookingRequest.setPeople(people);
        bookingRequest.setPaymentMethod(paymentMethod);

        HotelPostRequestDTO requestDTO = new HotelPostRequestDTO(
                "test@digitalhouse.com", bookingRequest);

        ObjectWriter writer = new ObjectMapper()
                .configure(SerializationFeature.WRAP_ROOT_VALUE, false)
                .registerModule(new JavaTimeModule())
                .writer().withDefaultPrettyPrinter();

        String payloadJson = writer.writeValueAsString(requestDTO);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/booking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadJson))
                .andDo(print()).andExpect(status().is4xxClientError()).andReturn();
        Assertions.assertEquals("application/json", response.getResponse().getContentType());
    }
}