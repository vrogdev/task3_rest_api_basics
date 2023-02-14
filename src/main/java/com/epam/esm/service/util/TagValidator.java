package com.epam.esm.service.util;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.ServiceExceptionCodes;
import com.epam.esm.service.exception.ServiceException;

/**
 * This class provides a validator for {@link Tag} entity.
 */
public class TagValidator {
    private static final int MAX_LENGTH_NAME = 40;
    private static final int MIN_LENGTH_NAME = 3;

    /**
     * Validates a {@link Tag} entity.
     *
     * @param item a {@link Tag} entity for validating.
     * @throws ServiceException if the entity contains incorrect fields.
     */
    public static void validate(Tag item) throws ServiceException {
        validateName(item.getName());
    }

    /**
     * Validates a {@link Tag} entity name.
     *
     * @param name a {@link Tag} name.
     * @throws ServiceException if name contains incorrect value.
     */
    public static void validateName(String name) throws ServiceException {
        if (name == null
                || name.length() < MIN_LENGTH_NAME
                || name.length() > MAX_LENGTH_NAME) {
            throw new ServiceException(ServiceExceptionCodes.BAD_TAG_NAME);
        }
    }
}
