package com.example.proyectofinal.dto.reservation;

import com.example.proyectofinal.dto.PaymentMethodDTO;
import com.example.proyectofinal.dto.PersonDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor
public class ReservationRequestDTO extends ReservationBaseDTO {
    @Valid
    private PaymentMethodDTO paymentMethod;

    public ReservationRequestDTO(ReservationBaseDTO base, PaymentMethodDTO paymentMethod) {
        this.setGoingDate(base.getGoingDate());
        this.setReturnDate(base.getReturnDate());
        this.setOrigin(base.getOrigin());
        this.setDestination(base.getDestination());
        this.setFlightNumber(base.getFlightNumber());
        this.setSeats(base.getSeats());
        this.setSeatType(base.getSeatType());
        this.setPeople(base.getPeople());
        this.setPaymentMethod(paymentMethod);
    }

    public ReservationRequestDTO(Integer id, LocalDate dateFrom, LocalDate dateTo, String origin,
                                 String destination, Integer flightNumber, BigDecimal seats,
                                 String seatType, Set<PersonDTO> people, PaymentMethodDTO paymentMethod) {
        super(id, dateFrom, dateTo, origin, destination, flightNumber, seats, seatType, people);
        this.setPaymentMethod(paymentMethod);
    }
}
