package com.epam.esm.service.util;

import com.epam.esm.service.exception.ServiceExceptionCodes;
import com.epam.esm.service.exception.ServiceException;

public class IdentifiableValidator {

    /**
     * Validates an entity ID.
     * @param id an entity ID.
     * @throws ServiceException if the ID contains incorrect value.
     */
    public static void validateId(long id) throws ServiceException {
        if (id < 1) {
            throw new ServiceException(ServiceExceptionCodes.BAD_ID);
        }
    }
}