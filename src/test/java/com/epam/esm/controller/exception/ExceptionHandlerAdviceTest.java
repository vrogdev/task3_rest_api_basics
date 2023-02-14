package com.epam.esm.controller.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ExceptionHandlerAdviceTest {

    @Mock
    private Environment environment;
    @InjectMocks
    private MessageProvider messageProvider;


    @Test
    void handleExceptions() {
        ExceptionHandlerAdvice advice = new ExceptionHandlerAdvice();

        Mockito.when(environment.getProperty("40404")).thenReturn("No objects found");
        Exception ex = new Exception("40404");

        ResponseEntity<ExceptionResponse> responseEntity = advice.handleExceptions(ex);
        ExceptionResponse body = responseEntity.getBody();

        Assertions.assertEquals("40404", body.getErrorCode());
        Assertions.assertEquals("No objects found", body.getErrorMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}