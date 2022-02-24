package com.example.proyectofinal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "HOTEL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    @Id
    @Column(name = "HOTEL_CODE")
    private String hotelCode;
    @Column(name = "NAME")
    private String name;
    @Column(name = "LOCATION")
    private String location;
    @Column(name = "ROOM_TYPE")
    private String roomType;
    @Column(name = "PRICE_PER_NIGHT")
    private Integer pricePerNight;
    @Column(name = "DATE_FROM")
    private LocalDate dateFrom;
    @Column(name = "DATE_TO")
    private LocalDate dateTo;
    @Column(name = "RESERVED")
    private Boolean reserved;
}
