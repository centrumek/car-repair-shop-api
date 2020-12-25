package com.carrepairshop.api.common;

import java.time.Instant;
import java.util.List;
import org.springframework.http.HttpStatus;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ApiError {

    Instant timestamp = Instant.now();
    HttpStatus code;
    int status;
    String message;
    List<String> errors;
    String path;
}
