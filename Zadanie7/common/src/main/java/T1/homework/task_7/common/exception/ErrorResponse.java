package T1.homework.task_7.common.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ErrorResponse (
        String message,
        HttpStatus status,
        ZonedDateTime timestamp
){}