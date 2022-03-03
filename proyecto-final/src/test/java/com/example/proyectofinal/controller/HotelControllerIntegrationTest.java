package com.example.proyectofinal.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
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
                        .param("dateFrom", "02/01/2022")
                        .param("dateTo", "04/01/2022")
                        .param("destination", "Ciudad de Mexico"))
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
                        .param("destination", "Houston"))
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
}
