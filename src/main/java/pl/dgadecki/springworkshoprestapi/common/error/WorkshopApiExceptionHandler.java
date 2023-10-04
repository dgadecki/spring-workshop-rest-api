package pl.dgadecki.springworkshoprestapi.common.error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class WorkshopApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException exception, HttpServletRequest request) {
        return ErrorResponse.builder()
                .uuid(UUID.randomUUID())
                .timestamp(LocalDateTime.now())
                .requestPath(request.getRequestURI())
                .message(exception.getMessage())
                .details(Arrays.toString(exception.getStackTrace()))
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception exception, HttpServletRequest request) {
        return ErrorResponse.builder()
                .uuid(UUID.randomUUID())
                .timestamp(LocalDateTime.now())
                .requestPath(request.getRequestURI())
                .message(exception.getMessage())
                .details(Arrays.toString(exception.getStackTrace()))
                .build();
    }
}
