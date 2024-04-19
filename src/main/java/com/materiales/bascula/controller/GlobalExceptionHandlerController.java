package com.materiales.bascula.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorReturn handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
        ErrorReturn tr = new ErrorReturn();
        tr.setPath(request.getRequestURI());
        tr.setStatus(HttpStatus.BAD_REQUEST.value());
        tr.setErrors(errors);
        tr.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return tr;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorReturn handleConstraintViolationException(ConstraintViolationException ex,
            HttpServletRequest request) {
        ErrorReturn tr = new ErrorReturn();
        tr.setPath(request.getRequestURI());
        tr.setStatus(HttpStatus.BAD_REQUEST.value());
        tr.setErrors(new ArrayList<String>(Arrays.asList(ex.getMessage().split(","))));
        tr.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return tr;
    }

    class ErrorReturn {

        private Timestamp timestamp;
        private int status;
        private List<String> errors;
        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Timestamp getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<String> getErrors() {
            return errors;
        }

        public void setErrors(List<String> errors) {
            this.errors = errors;
        }

    }

}
