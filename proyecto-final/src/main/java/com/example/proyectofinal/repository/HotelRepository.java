package com.example.proyectofinal.repository;

import com.example.proyectofinal.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, String> {
}
