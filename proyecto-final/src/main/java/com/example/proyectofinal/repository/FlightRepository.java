package com.example.proyectofinal.repository;

import com.example.proyectofinal.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
