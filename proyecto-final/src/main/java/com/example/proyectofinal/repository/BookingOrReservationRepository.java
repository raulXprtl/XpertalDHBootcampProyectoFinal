package com.example.proyectofinal.repository;

import com.example.proyectofinal.entity.BookingOrReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingOrReservationRepository extends JpaRepository<BookingOrReservation, Integer> {
}
