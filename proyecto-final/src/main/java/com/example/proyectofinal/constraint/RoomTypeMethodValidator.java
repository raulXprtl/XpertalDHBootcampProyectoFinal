package com.example.proyectofinal.constraint;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class RoomTypeMethodValidator implements ConstraintValidator<RoomTypePeopleMatch, Object> {
    private String roomType;
    private String peopleAmount;

    public void initialize(RoomTypePeopleMatch constraintAnnotation) {
        this.roomType = constraintAnnotation.roomType();
        this.peopleAmount = constraintAnnotation.peopleAmount();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            String roomType = (String) new BeanWrapperImpl(value).getPropertyValue(this.roomType);
            BigDecimal peopleAmount = (BigDecimal) new BeanWrapperImpl(value)
                    .getPropertyValue(this.peopleAmount);

            assert roomType != null;
            assert peopleAmount != null;

            switch (roomType){
                case "Single": return peopleAmount.compareTo(BigDecimal.ONE) == 0;
                case "Doble": return peopleAmount.compareTo(new BigDecimal(2)) == 0;
                case "Triple": return peopleAmount.compareTo(new BigDecimal(3)) == 0;
                case "Múltiple":
                    return peopleAmount.compareTo(new BigDecimal(3)) > 0 &&
                            peopleAmount.compareTo(new BigDecimal(6)) < 0;
                default: return false;
            }
        } catch (Exception exception){
            System.out.println(exception.toString());
            return false;
        }
    }
}
