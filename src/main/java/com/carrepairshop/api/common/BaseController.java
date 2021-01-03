package com.carrepairshop.api.common;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class BaseController extends ResponseEntityExceptionHandler {

    // https://www.baeldung.com/global-error-handler-in-a-spring-rest-api
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        return ResponseEntity.badRequest()
                             .body(ApiError.builder()
                                           .code(BAD_REQUEST)
                                           .status(BAD_REQUEST.value())
                                           .message("Validation failed")
                                           .errors(parseErrors(ex))
                                           .path(getRequestURI(request))
                                           .build());
    }

    private List<String> parseErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                 .getAllErrors()
                 .stream()
                 .map(objectError -> (FieldError) objectError)
                 .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                 .collect(Collectors.toList());
    }

    private String getRequestURI(final WebRequest webRequest) {
        return ((ServletWebRequest) webRequest).getRequest().getRequestURI();
    }

    @ExceptionHandler(EntityExistsException.class)
    public ApiError handleEntityExistsException(EntityExistsException ex,
                                                WebRequest request) {
        return ApiError.builder()
                       .code(BAD_REQUEST)
                       .status(BAD_REQUEST.value())
                       .message(ex.getLocalizedMessage())
                       .path(getRequestURI(request))
                       .build();
    }

    @ExceptionHandler(value = {PropertyReferenceException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiError handlePropertyReferenceException(PropertyReferenceException ex,
                                                     WebRequest request) {
        return ApiError.builder()
                       .code(BAD_REQUEST)
                       .status(BAD_REQUEST.value())
                       .message(ex.getLocalizedMessage())
                       .path(getRequestURI(request))
                       .build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ApiError handleEntityNotFoundException(EntityNotFoundException ex,
                                                  WebRequest request) {
        return ApiError.builder()
                       .code(NOT_FOUND)
                       .status(NOT_FOUND.value())
                       .message(ex.getLocalizedMessage())
                       .path(getRequestURI(request))
                       .build();
    }
}
