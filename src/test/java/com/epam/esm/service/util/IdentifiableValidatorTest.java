package com.epam.esm.service.util;

import com.epam.esm.service.exception.ServiceExceptionCodes;
import com.epam.esm.service.exception.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IdentifiableValidatorTest {

    @Test
    void validateCorrectId() {
        Assertions.assertDoesNotThrow(() -> IdentifiableValidator.validateId(10));
    }

    @Test
    void validateIncorrectId() {
        ServiceException exception = Assertions.assertThrows(ServiceException.class, () -> IdentifiableValidator.validateId(-1));
        Assertions.assertEquals(ServiceExceptionCodes.BAD_ID, exception.getMessage());
    }
}