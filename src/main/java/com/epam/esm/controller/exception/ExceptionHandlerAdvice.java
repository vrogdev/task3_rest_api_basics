package com.epam.esm.controller.exception;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.service.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

        @ExceptionHandler({
                DaoException.class,
                ServiceException.class
        })
        public ResponseEntity<ExceptionResponse> handleExceptions(Exception exception) {
            ExceptionResponse exceptionResponse = new ExceptionResponse();

            String exceptionCode = exception.getMessage();
            String details = MessageProvider.getMessage(exceptionCode);

            exceptionResponse.setErrorCode(exceptionCode);
            exceptionResponse.setErrorMessage(details);

            HttpStatus httpStatus = parseHttpStatus(exceptionCode);
            return new ResponseEntity<>(exceptionResponse, httpStatus);
        }

        private HttpStatus parseHttpStatus(String exceptionCode) {
            int httpStatusCode = Integer.parseInt(exceptionCode.substring(0,3));
            return HttpStatus.valueOf(httpStatusCode);
        }
}
