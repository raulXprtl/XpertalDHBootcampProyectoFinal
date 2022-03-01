package com.example.proyectofinal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
@Table(name = "tourist_pack")
public class TouristPack {
    @Id
    @Column(name = "id_tourist_pack")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer touristPackId;
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_FK", referencedColumnName = "id_customer")
    private Customer customer;
}
