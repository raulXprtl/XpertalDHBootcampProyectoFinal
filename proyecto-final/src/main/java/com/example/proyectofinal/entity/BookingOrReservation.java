package com.example.proyectofinal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "book_res_customer")
public class BookingOrReservation {
    @Id
    @Column(name = "id_book_res_customer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookingReservationId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_FK", referencedColumnName = "id_customer")
    private Customer customer;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_FK", referencedColumnName = "id_booking")
    private Booking booking;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_FK", referencedColumnName = "id_reservation")
    private Reservation reservation;
}
