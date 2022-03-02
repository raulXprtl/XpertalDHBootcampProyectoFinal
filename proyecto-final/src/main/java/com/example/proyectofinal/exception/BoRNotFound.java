package com.example.proyectofinal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Booking or Reservation not found")
public class BoRNotFound extends RuntimeException {
}
