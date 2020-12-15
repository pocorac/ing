package com.ing.accm.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class RestErrorResponse {

    private HttpStatus status;
    private String message;

    RestErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
