package com.example.proyectofinal.dto;

import com.example.proyectofinal.constraint.PaymentDuesTypeMatch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@PaymentDuesTypeMatch(type = "type", dues = "dues", message = "Cuotas no válidas para tipo de pago especificado")
public class PaymentMethodDTO {
    private String type;
    private String number;

    @NotNull
    @DecimalMax(value = "48")
    @Digits(integer = 2, fraction = 0, message = "La cantidad de cuotas debe ser un valor numérico de máximo 2 dígitos")
    private BigDecimal dues;

    public double calculateInterest() {
        double interest = 0.0;
        if (this.getType().equalsIgnoreCase("CREDIT")) {
            interest = Math.ceil(this.getDues().intValue() / 3.0) * 5;
        }
        return interest;
    }
}
