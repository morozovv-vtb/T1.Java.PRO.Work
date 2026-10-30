package T1.homework.task_7.common.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ErrorResponse (
        String message,
        HttpStatus status,
        ZonedDateTime timestamp
){
    public ErrorResponse(String message, org.springframework.http.HttpStatus httpStatus, ZonedDateTime now) {
    }
}