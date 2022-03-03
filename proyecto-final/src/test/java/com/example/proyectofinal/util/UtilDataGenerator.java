package com.example.proyectofinal.util;

import com.example.proyectofinal.dto.PersonDTO;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class UtilDataGenerator {
    public static Set<PersonDTO> generatePeople(int count) {
        Set<PersonDTO> people = new HashSet<>();
        for (int ii = 0; ii < count; ii++) {
            people.add(new PersonDTO(
                    String.format("TestDni%d", ii),
                    "TestFName",
                    "TestLName",
                    LocalDate.of(2000, 1, 1),
                    "test@xpertal.com"
            ));
        }
        return people;
    }
}
