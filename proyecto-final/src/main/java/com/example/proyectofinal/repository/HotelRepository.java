package com.example.proyectofinal.repository;

import com.example.proyectofinal.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE hotels h SET h.reserved = 0 WHERE h.id_hotel = ?", nativeQuery = true)
    int updateHotelReservedStatusForId(Integer id);
}
