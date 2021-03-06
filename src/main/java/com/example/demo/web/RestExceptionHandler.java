package com.example.demo.web;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.example.demo.exception.ApiError;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {

    ApiError apiError = new ApiError(NOT_FOUND, ex.getMessage());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  protected ResponseEntity<Object> handleInvalidParameterException(IllegalArgumentException ex) {

    ApiError apiError = new ApiError(BAD_REQUEST, ex.getMessage());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<Object> handleConstraintViolationException(
      ConstraintViolationException ex) {

    ApiError apiError = new ApiError(BAD_REQUEST, ex.getMessage());
    return buildResponseEntity(apiError);
  }

  @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
  protected ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(
      SQLIntegrityConstraintViolationException ex) {

    ApiError apiError = new ApiError(BAD_REQUEST, ex.getMessage());
    return buildResponseEntity(apiError);
  }

  @Override
  protected ResponseEntity<Object> handleBindException(
      BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    Map<String, String> errors =
        ex.getAllErrors().stream()
            .filter(error -> !StringUtils.isEmpty(error.getDefaultMessage()))
            .collect(
                Collectors.toMap(
                    error -> ((FieldError) error).getField(),
                    DefaultMessageSourceResolvable::getDefaultMessage,
                    (existing, replacement) -> existing + " & " + replacement));

    ApiError apiError = new ApiError(BAD_REQUEST, errors.toString());
    return buildResponseEntity(apiError);
  }

  private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(apiError, apiError.getStatus());
  }
}
