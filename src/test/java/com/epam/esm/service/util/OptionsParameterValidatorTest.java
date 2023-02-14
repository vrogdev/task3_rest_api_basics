package com.epam.esm.service.util;

import com.epam.esm.dao.util.constants.OptionParameter;
import com.epam.esm.service.exception.ServiceExceptionCodes;
import com.epam.esm.service.exception.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class OptionsParameterValidatorTest {
    @Test
    void testSortTypeDirection() {
        Assertions.assertDoesNotThrow(() -> OptionsParameterValidator.validateSortType("ASC"));
        Assertions.assertDoesNotThrow(() -> OptionsParameterValidator.validateSortType("DESC"));

        ServiceException ex = Assertions.assertThrows(ServiceException.class, () -> OptionsParameterValidator.validateSortType("de"));
        Assertions.assertEquals(ServiceExceptionCodes.BAD_SORTING_TYPE,ex.getMessage());
    }

    @Test
    void testValidateGiftCertificateRequestParameters() {
        Map<String, String> correctParameters = new HashMap<>();
        correctParameters.put(OptionParameter.NAME, "");
        correctParameters.put(OptionParameter.DESCRIPTION, "");
        correctParameters.put(OptionParameter.TAG_NAME, "");
        correctParameters.put(OptionParameter.SORT_BY_DATE, "ASC");
        correctParameters.put(OptionParameter.SORT_BY_NAME, "ASC");

        Map<String, String> incorrectParameters = new HashMap<>();
        incorrectParameters.put("bad key value", "");

        Assertions.assertDoesNotThrow(() -> OptionsParameterValidator.validateGiftCertificateRequestParameters(correctParameters));

        ServiceException ex = Assertions.assertThrows(ServiceException.class, () -> OptionsParameterValidator.validateGiftCertificateRequestParameters(incorrectParameters));
        Assertions.assertEquals(ServiceExceptionCodes.BAD_GIFT_CERTIFICATE_FILTER_PARAMETER,ex.getMessage());
    }

    @Test
    void testValidateTagParameters() {
        Map<String, String> correctParameters = new HashMap<>();
        correctParameters.put(OptionParameter.TAG_NAME, "");
        correctParameters.put(OptionParameter.SORT_BY_TAG_NAME, "ASC");

        Map<String, String> incorrectParameters = new HashMap<>();
        incorrectParameters.put("bad key value", "");

        Assertions.assertDoesNotThrow(() -> OptionsParameterValidator.validateTagParameters(correctParameters));

        ServiceException ex = Assertions.assertThrows(ServiceException.class, () -> OptionsParameterValidator.validateTagParameters(incorrectParameters));
        Assertions.assertEquals(ServiceExceptionCodes.BAD_GIFT_CERTIFICATE_FILTER_PARAMETER,ex.getMessage());
    }
}