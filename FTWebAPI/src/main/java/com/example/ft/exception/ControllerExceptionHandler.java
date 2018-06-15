package com.example.ft.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@ControllerAdvice("com.example.ft")
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    public static void handle(TransactionSystemException e) {
        //e.printStackTrace();
        if (e.getMostSpecificCause() instanceof ConstraintViolationException) {
            List<String> messages = new ArrayList<>();

            Collector<? super ConstraintViolation<?>, ?, String> a;

            String message = ((ConstraintViolationException) e.getMostSpecificCause())
                    .getConstraintViolations()
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("\n"));

            throw new BadRequestException(message);
        }
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity<ServiceErrorOutputDto> handleAnyException(Exception e) {
        ServiceException serviceException;

        if (e instanceof ServiceException) {
            serviceException = (ServiceException) e;
        } else if (e instanceof AccessDeniedException) {
            serviceException = new UnauthorizedException("Access not allowed");
        } else {
            serviceException = new InternalServerErrorException(e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
        }

        final ServiceErrorOutputDto body = ServiceErrorOutputDto.fromException(serviceException);

        return new ResponseEntity<>(body, serviceException.getHttpStatus());
    }

}
