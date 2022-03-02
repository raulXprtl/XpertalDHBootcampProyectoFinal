package com.example.proyectofinal.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "payments")
public class PaymentMethod {
    @Id
    @Column(name = "id_payment")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;
    @Column(name = "type")
    private String type;
    @Column(name = "number")
    private String number;
    @Column(name = "dues")
    private Integer dues;

    @JsonManagedReference
    @Column(nullable = true)
    @OneToMany(mappedBy = "paymentMethod", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Booking> bookings;

    @JsonManagedReference
    @Column(nullable = true)
    @OneToMany(mappedBy = "paymentMethod", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Reservation> reservations;
}
