package com.epam.esm.controller.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class MessageProvider {
    private static Environment environment;

    @Autowired
    MessageProvider(Environment environment) {
        MessageProvider.environment = environment;
    }

    public static String getMessage(String code) {
        return environment.getProperty(code);
    }
}
