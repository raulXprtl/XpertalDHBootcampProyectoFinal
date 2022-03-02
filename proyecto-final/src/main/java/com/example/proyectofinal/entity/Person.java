package com.example.proyectofinal.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "people")
public class Person {
    @Id
    @Column(name = "id_person")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer personId;
    @Column(name = "dni")
    private String dni;
    @Column(name = "first_name")
    private String firstname;
    @Column(name = "last_name")
    private String lastname;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "email")
    private String mail;

    @JsonIgnoreProperties("people")
    @ManyToMany(mappedBy = "people")
    private Set<Booking> bookings;

    @JsonIgnoreProperties("people")
    @ManyToMany(mappedBy = "people")
    private Set<Reservation> reservations;
}
