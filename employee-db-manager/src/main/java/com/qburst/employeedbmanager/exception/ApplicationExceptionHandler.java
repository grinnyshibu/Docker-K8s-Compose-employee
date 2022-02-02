package com.qburst.employeedbmanager.exception;

import com.qburst.employeedbmanager.dto.ErrorMessageDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception exception, WebRequest request) {

        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(new Date(), exception.getLocalizedMessage());

        return new ResponseEntity<>(errorMessageDTO, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {EmployeeNotFoundException.class})
    public ResponseEntity<Object> handleEmployeeMissingException(EmployeeNotFoundException exception, WebRequest request) {

        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(new Date(), exception.getLocalizedMessage());

        return new ResponseEntity<>(errorMessageDTO, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {HttpStatusCodeException.class})
    public ResponseEntity<Object> handleHttpException(HttpStatusCodeException exception, WebRequest request) {

        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(new Date(), exception.getLocalizedMessage());

        return new ResponseEntity<>(errorMessageDTO, new HttpHeaders(), exception.getStatusCode());
    }
}
