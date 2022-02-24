package com.example.proyectofinal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "FLIGHT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    @Column(name = "FLIGHT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long flightId;
    @Column(name = "FLIGHT_NUMBER")
    private String flightNumber;
    @Column(name = "ORIGIN")
    private String origin;
    @Column(name = "DESTINATION")
    private String destination;
    @Column(name = "SEAT_TYPE")
    private String seatType;
    @Column(name = "PRICE_PER_PERSON")
    private Double pricePerPerson;
    @Column(name = "DATE_FROM")
    private LocalDate dateFrom;
    @Column(name = "DATE_TO")
    private LocalDate dateTo;
}
