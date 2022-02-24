package com.example.proyectofinal.dto.flight;

import com.example.proyectofinal.dto.PaymentMethodDTO;
import com.example.proyectofinal.dto.PersonDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class FlightReservationRequestDTO extends FlightReservationBaseDTO {
    @Valid
    private PaymentMethodDTO paymentMethod;

    public FlightReservationRequestDTO(FlightReservationBaseDTO base, PaymentMethodDTO paymentMethod) {
        this.setDateFrom(base.getDateFrom());
        this.setDateTo(base.getDateTo());
        this.setOrigin(base.getOrigin());
        this.setDestination(base.getDestination());
        this.setFlightNumber(base.getFlightNumber());
        this.setSeats(base.getSeats());
        this.setSeatType(base.getSeatType());
        this.setPeople(base.getPeople());
        this.setPaymentMethod(paymentMethod);
    }

    public FlightReservationRequestDTO(LocalDate dateFrom, LocalDate dateTo, String origin,
                                       String destination, String flightNumber, BigDecimal seats,
                                       String seatType, List<PersonDTO> people, PaymentMethodDTO paymentMethod) {
        super(dateFrom, dateTo, origin, destination, flightNumber, seats, seatType, people);
        this.setPaymentMethod(paymentMethod);
    }
}
