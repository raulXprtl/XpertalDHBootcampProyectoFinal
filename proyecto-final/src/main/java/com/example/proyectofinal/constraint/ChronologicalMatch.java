package com.example.proyectofinal.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ChronologicalMethodValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ChronologicalMatch {
    String dateFrom();
    String dateTo();
    String message() default "{error.dateChronology}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
