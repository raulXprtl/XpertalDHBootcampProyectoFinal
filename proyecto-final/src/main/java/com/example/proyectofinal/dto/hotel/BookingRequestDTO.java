package com.example.proyectofinal.dto.hotel;

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
public class BookingRequestDTO extends BookingBaseDTO {
    @Valid
    private PaymentMethodDTO paymentMethod;

    public BookingRequestDTO(BookingBaseDTO base, PaymentMethodDTO paymentMethod) {
        this.setDateFrom(base.getDateFrom());
        this.setDateTo(base.getDateTo());
        this.setDestination(base.getDestination());
        this.setHotelCode(base.getHotelCode());
        this.setPeopleAmount(base.getPeopleAmount());
        this.setRoomType(base.getRoomType());
        this.setPeople(base.getPeople());
        this.setPaymentMethod(paymentMethod);
    }

    public BookingRequestDTO(Long id, LocalDate dateFrom, LocalDate dateTo, String destination,
                             String hotelCode, BigDecimal peopleAmount, String roomType,
                             List<PersonDTO> people, PaymentMethodDTO paymentMethod) {
        super(id, dateFrom, dateTo, destination, hotelCode, peopleAmount, roomType, people);
        this.setPaymentMethod(paymentMethod);
    }
}
