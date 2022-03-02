package com.example.proyectofinal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "reservations")
public class Reservation {
    @Id
    @Column(name = "id_reservation")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_FK", referencedColumnName = "id_flight")
    private Flight flight;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_FK", referencedColumnName = "id_payment")
    private PaymentMethod paymentMethod;

    @JsonIgnoreProperties("reservations")
    @ManyToMany
    @JoinTable(
            name = "reservation_people",
            joinColumns = @JoinColumn(name = "reservation_FK"),
            inverseJoinColumns = @JoinColumn(name = "person_FK", referencedColumnName = "id_person"))
    private Set<Person> people;
}
