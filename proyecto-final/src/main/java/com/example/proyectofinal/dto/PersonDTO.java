package com.example.proyectofinal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    private String dni;
    private String firstname;
    private String lastname;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    private String mail;
}
