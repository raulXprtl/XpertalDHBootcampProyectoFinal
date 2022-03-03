package com.example.proyectofinal.repository;

import com.example.proyectofinal.entity.Hotel;
import com.example.proyectofinal.util.HotelDataGenerator;
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
class HotelRepositoryUnitTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HotelRepository repo;

    @Test
    void findAll() {
        Iterable<Hotel> hotels = repo.findAll();
        assertThat(hotels).isNotEmpty();
    }

    @Test
    void findNonExistingHotel() {
        Hotel hotel = HotelDataGenerator.getHotelTest("Test Hotel", "Test Dest.");
        entityManager.persist(hotel);
        entityManager.remove(hotel);
        assertThat(repo.findById(hotel.getHotelCode())).isEmpty();
    }

    @Test
    void findExistingHotel() {
        Hotel hotel = HotelDataGenerator.getHotelTest("Test Hotel", "Test Dest.");
        entityManager.persist(hotel);
        assertThat(repo.findAll()).contains(hotel);
        entityManager.remove(hotel);
    }
}
