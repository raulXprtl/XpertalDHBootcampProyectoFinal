package com.example.proyectofinal.constraint;

import com.example.proyectofinal.dto.PaymentMethodDTO;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class PaymentMethodValidator implements ConstraintValidator<PaymentDuesTypeMatch, PaymentMethodDTO> {
    private String type;
    private String dues;

    public void initialize(PaymentDuesTypeMatch constraintAnnotation) {
        this.type = constraintAnnotation.type();
        this.dues = constraintAnnotation.dues();
    }

    public boolean isValid(PaymentMethodDTO value, ConstraintValidatorContext context) {
        try {
            Object paymentType = new BeanWrapperImpl(value).getPropertyValue(type);
            BigDecimal paymentDues = (BigDecimal) new BeanWrapperImpl(value).getPropertyValue(dues);

            assert paymentType != null;
            assert paymentDues != null;

            if (paymentType.toString().equals("DEBIT"))
                return paymentDues.compareTo(BigDecimal.ONE) == 0;
            return paymentDues.compareTo(BigDecimal.ONE) >= 0;
        } catch (Exception exception) {
            System.out.println(exception.toString());
            return false;
        }
    }
}
