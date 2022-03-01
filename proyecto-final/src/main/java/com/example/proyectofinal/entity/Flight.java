package com.example.proyectofinal.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "flights")
public class Flight {
    @Id
    @Column(name = "id_flight")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long flightId;
    @Column(name = "flight_number")
    private String flightNumber;
    @Column(name = "origin")
    private String origin;
    @Column(name = "destination")
    private String destination;
    @Column(name = "seat_type")
    private String seatType;
    @Column(name = "price_per_person")
    private Double pricePerPerson;
    @Column(name = "date_from")
    private LocalDate dateFrom;
    @Column(name = "date_to")
    private LocalDate dateTo;

    @JsonManagedReference
    @Column(nullable = true)
    @OneToMany(mappedBy = "flight", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Reservation> reservations;
}
