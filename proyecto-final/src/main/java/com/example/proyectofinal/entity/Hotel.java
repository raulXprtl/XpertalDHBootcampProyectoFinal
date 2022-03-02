package com.example.proyectofinal.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "hotels")
public class Hotel {
    @Id
    @Column(name = "id_hotel")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hotelCode;
    @Column(name = "name")
    private String name;
    @Column(name = "location")
    private String place;
    @Column(name = "room_type")
    private String roomType;
    @Column(name = "price_per_night")
    private Double roomPrice;
    @Column(name = "date_from")
    private LocalDate disponibilityDateFrom;
    @Column(name = "date_to")
    private LocalDate disponibilityDateTo;
    @Column(name = "reserved")
    private Boolean isBooking;

    @JsonManagedReference
    @Column(nullable = true)
    @OneToMany(mappedBy = "hotel", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Booking> bookings;
}
