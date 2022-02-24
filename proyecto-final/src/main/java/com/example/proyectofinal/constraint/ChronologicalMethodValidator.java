package com.example.proyectofinal.constraint;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ChronologicalMethodValidator implements ConstraintValidator<ChronologicalMatch, Object> {
    private String dateFrom;
    private String dateTo;

    public void initialize(ChronologicalMatch constraintAnnotation) {
        this.dateFrom = constraintAnnotation.dateFrom();
        this.dateTo = constraintAnnotation.dateTo();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            LocalDate dateFrom = (LocalDate) new BeanWrapperImpl(value).getPropertyValue(this.dateFrom);
            LocalDate dateTo = (LocalDate) new BeanWrapperImpl(value).getPropertyValue(this.dateTo);

            assert dateFrom != null;
            assert dateTo != null;

            return dateTo.compareTo(dateFrom) >= 0;
        } catch (Exception exception) {
            System.out.println(exception.toString());
            return false;
        }
    }
}
