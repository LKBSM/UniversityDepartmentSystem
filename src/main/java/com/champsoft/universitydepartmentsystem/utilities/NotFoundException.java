package com.champsoft.universitydepartmentsystem.utilities;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// R11: Custom exception that will be mapped to HTTP 404 Not Found by @ControllerAdvice.
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(String resourceName, Long id) {
        super(String.format("%s with ID %d not found.", resourceName, id));
    }

    public NotFoundException(String message) {
        super(message);
    }
}