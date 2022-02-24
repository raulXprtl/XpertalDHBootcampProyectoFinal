package com.example.proyectofinal.repository;

import com.example.proyectofinal.entity.Flight;
import com.example.proyectofinal.util.FlightDataGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FlightRepositoryUnitTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FlightRepository repo;

    @Test
    void findAll() {
        Iterable<Flight> flights =  repo.findAll();
        assertThat(flights).isNotEmpty();
    }

    @Test
    void findNonExistingFlight() {
        Flight flight = FlightDataGenerator.getFlightTest(1, "MTY", "CDMX");
        assertThat(repo.findById(flight.getFlightId())).isEmpty();
    }

    @Test
    void findExistingFlight() {
        Flight flight = FlightDataGenerator.getFlightTest(1, "MTY", "CDMX");
        entityManager.persist(flight);
        assertThat(repo.findById(flight.getFlightId())).isNotEmpty();
    }
}
