package com.epam.esm.dao.util.constants;

import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ServiceExceptionCodes;

public enum SortingDirection {
    ASC("ASC"),
    DESC("DESC");

    private String value;

    SortingDirection(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SortingDirection getByValue(String value) throws ServiceException {
        SortingDirection direction;

        if(value.equalsIgnoreCase("asc")) {
            direction = ASC;
        } else if (value.equalsIgnoreCase("desc")) {
            direction = DESC;
        } else {
            throw new ServiceException(ServiceExceptionCodes.BAD_GIFT_CERTIFICATE_FILTER_PARAMETER);
        }

        return direction;
    }
}
