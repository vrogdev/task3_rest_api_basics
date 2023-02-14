package com.epam.esm.service.util;

import com.epam.esm.dao.util.constants.OptionParameter;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ServiceExceptionCodes;

import java.util.List;
import java.util.Map;

public class OptionsParameterValidator {
    private static final String ASCENDING = "ASC";
    private static final String DESCENDING = "DESC";

    public static void validateSortType(String value) throws ServiceException {
        if (value == null
                || (!value.equalsIgnoreCase(ASCENDING) && !value.equalsIgnoreCase(DESCENDING))) {
            throw new ServiceException(ServiceExceptionCodes.BAD_SORTING_TYPE);
        }
    }

    public static void validateGiftCertificateRequestParameters(Map<String, String> requestParams) throws ServiceException {
        List<String> validationParameters = List.of(
                OptionParameter.NAME,
                OptionParameter.DESCRIPTION,
                OptionParameter.TAG_NAME,
                OptionParameter.SORT_BY_DATE,
                OptionParameter.SORT_BY_NAME);

        List<String> sortValidationParameters = List.of(
                OptionParameter.SORT_BY_DATE,
                OptionParameter.SORT_BY_NAME);

        for (Map.Entry<String, String> entry : requestParams.entrySet()) {
            if (!validationParameters.contains(entry.getKey())) {
                throw new ServiceException(ServiceExceptionCodes.BAD_GIFT_CERTIFICATE_FILTER_PARAMETER);
            }

            if (sortValidationParameters.contains(entry.getKey())) {
                OptionsParameterValidator.validateSortType(entry.getValue());
            }
        }

    }

    public static void validateTagParameters(Map<String, String> requestParams) throws ServiceException {
        List<String> validationParameters = List.of(
                OptionParameter.TAG_NAME,
                OptionParameter.SORT_BY_TAG_NAME);

        List<String> sortValidationParameters = List.of(OptionParameter.SORT_BY_TAG_NAME);

        for (Map.Entry<String, String> entry : requestParams.entrySet()) {
            if (!validationParameters.contains(entry.getKey())) {
                throw new ServiceException(ServiceExceptionCodes.BAD_TAG_FILTER_PARAMETER);
            }

            if (sortValidationParameters.contains(entry.getKey())) {
                OptionsParameterValidator.validateSortType(entry.getValue());
            }
        }
    }

}
