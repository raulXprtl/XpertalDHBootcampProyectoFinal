package com.example.proyectofinal.repository;

import com.example.proyectofinal.entity.TouristPack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TouristPackRepository extends JpaRepository<TouristPack, Integer> {
}
