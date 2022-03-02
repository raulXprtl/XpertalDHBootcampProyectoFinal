package com.example.proyectofinal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "bookings")
public class Booking {
    @Id
    @Column(name = "id_booking")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingId;
    @Column(name = "date_from")
    private LocalDate dateFrom;
    @Column(name = "date_to")
    private LocalDate dateTo;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_FK", referencedColumnName = "id_hotel")
    private Hotel hotel;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_FK", referencedColumnName = "id_payment")
    private PaymentMethod paymentMethod;

    @JsonIgnoreProperties("bookings")
    @ManyToMany
    @JoinTable(
            name = "booking_people",
            joinColumns = @JoinColumn(name = "booking_FK"),
            inverseJoinColumns = @JoinColumn(name = "person_FK", referencedColumnName = "id_person"))
    private Set<Person> people;
}
