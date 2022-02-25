package com.example.proyectofinal.dto.flight;

import com.example.proyectofinal.dto.PaymentMethodDTO;
import com.example.proyectofinal.dto.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private Long reservation_id;
    private LocalDate goingDate;
    private LocalDate returnDate;
    private String origin;
    private String destination;
    private Integer seats;
    private String seatType;
    private List<PersonDTO> people;
    private PaymentMethodDTO paymentMethod;


    // <-- Se requiere la entidad de reservaciones
}
