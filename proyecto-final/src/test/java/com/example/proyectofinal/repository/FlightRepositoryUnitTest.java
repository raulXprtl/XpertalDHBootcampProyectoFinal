package com.example.proyectofinal.repository;

import com.example.proyectofinal.entity.Flight;
import com.example.proyectofinal.util.FlightDataGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class FlightRepositoryUnitTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FlightRepository repo;

    @Test
    void findAll() {
        Iterable<Flight> flights = repo.findAll();
        assertThat(flights).isNotEmpty();
    }

    @Test
    void findNonExistingFlight() {
        Flight flight = FlightDataGenerator.getFlightTest("MTY", "CDMX");
        entityManager.persist(flight);
        entityManager.remove(flight);
        assertThat(repo.findById(flight.getFlightNumber())).isEmpty();
    }

    @Test
    void findExistingFlight() {
        Flight flight = FlightDataGenerator.getFlightTest("MTY", "CDMX");
        entityManager.persist(flight);
        assertThat(repo.findById(flight.getFlightNumber())).isNotEmpty();
        entityManager.remove(flight);
    }
}
