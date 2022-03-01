package com.example.proyectofinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "people")
public class Person {
    @Id
    @Column(name = "id_person")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer personId;
    @Column(name = "dni")
    private String dni;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "email")
    private String email;

    @JsonIgnoreProperties("people")
    @ManyToMany
    @JoinTable(
            name = "booking_people",
            joinColumns = @JoinColumn(name = "person_FK"),
            inverseJoinColumns = @JoinColumn(name = "booking_FK"))
    private Set<Booking> bookings;

    @JsonIgnoreProperties("people")
    @ManyToMany
    @JoinTable(
            name = "reservation_people",
            joinColumns = @JoinColumn(name = "person_FK"),
            inverseJoinColumns = @JoinColumn(name = "reservation_FK"))
    private Set<Reservation> reservations;
}
