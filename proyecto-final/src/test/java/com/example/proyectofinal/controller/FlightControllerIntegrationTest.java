package com.example.proyectofinal.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
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
                        .param("dateFrom", "03/03/2022")
                        .param("dateTo", "09/03/2022")
                        .param("origin", "Monterrey")
                        .param("destination", "Queretaro"))
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
}
