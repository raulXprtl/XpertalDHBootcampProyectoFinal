package com.example.proyectofinal.exception;

import com.example.proyectofinal.dto.StatusCodeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionConfig {

    /**
     * This method controls the exception for no available items requests.
     * @param noSuchElementException exception type.
     * @return exception as a response entity.
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<StatusCodeDTO> noSuchElementHandler(
            NoSuchElementException noSuchElementException) {
        StatusCodeDTO status = new StatusCodeDTO(
                HttpStatus.NOT_FOUND.value(),
                noSuchElementException.getMessage());
        return new ResponseEntity<>(status, HttpStatus.NOT_FOUND);
    }

    /**
     * This method controls the exception for illegal or duplicate item requests.
     * @param illegalArgumentException exception type.
     * @return exception as a response entity.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StatusCodeDTO> illegalArgumentHandler(
            IllegalArgumentException illegalArgumentException) {
        StatusCodeDTO status = new StatusCodeDTO(
                HttpStatus.CONFLICT.value(),
                illegalArgumentException.getMessage());
        return new ResponseEntity<>(status, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<StatusCodeDTO>> constraintViolationHandler(
            ConstraintViolationException methodArgumentNotValidException){
        List<StatusCodeDTO> errorList = new ArrayList<>();
        for (ConstraintViolation violation :
                methodArgumentNotValidException.getConstraintViolations()) {
            errorList.add(new StatusCodeDTO(HttpStatus.BAD_REQUEST.value(), violation.getMessage()));
        }
        return new ResponseEntity<>(errorList, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<StatusCodeDTO> missingServletRequestParameterHandler(
            MissingServletRequestParameterException missingServletRequestParameterException){
        StatusCodeDTO error = new StatusCodeDTO(
                HttpStatus.BAD_REQUEST.value(),
                missingServletRequestParameterException.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<StatusCodeDTO> dateTimeParseHandler(
            DateTimeParseException dateTimeParseException){
        StatusCodeDTO error = new StatusCodeDTO(
                HttpStatus.BAD_REQUEST.value(),
                String.format("Formato de fecha debe ser dd/mm/aaaa: %s",
                        dateTimeParseException.getParsedString())
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<StatusCodeDTO> dateTimeHandler(
            DateTimeException dateTimeException){
        StatusCodeDTO error = new StatusCodeDTO(
                HttpStatus.BAD_REQUEST.value(),
                dateTimeException.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<StatusCodeDTO>> methodArgumentNotValidHandler(
            MethodArgumentNotValidException methodArgumentNotValidException){
        List<StatusCodeDTO> errorList = new ArrayList<>();
        for (ObjectError error :
                methodArgumentNotValidException.getAllErrors()) {
            errorList.add(new StatusCodeDTO(HttpStatus.BAD_REQUEST.value(), error.getDefaultMessage()));
        }
        return new ResponseEntity<>(errorList, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StatusCodeDTO> httpMessageNotReadableHandler(
            HttpMessageNotReadableException httpMessageNotReadableException
    ){
        if (httpMessageNotReadableException.getRootCause() != null){
            Throwable cause = httpMessageNotReadableException.getRootCause();
            if (cause.getClass().equals(DateTimeParseException.class))
                return dateTimeParseHandler((DateTimeParseException) cause);
            if (cause.getClass().equals(DateTimeException.class))
                return dateTimeHandler((DateTimeException) cause);
        }
        StatusCodeDTO errorDTO = new StatusCodeDTO(
                HttpStatus.BAD_REQUEST.value(), httpMessageNotReadableException.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
}
