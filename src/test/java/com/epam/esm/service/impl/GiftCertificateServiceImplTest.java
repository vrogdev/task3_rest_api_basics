package com.epam.esm.service.impl;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.dao.interfaces.GiftCertificateDAO;
import com.epam.esm.dao.util.constants.OptionParameter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ServiceExceptionCodes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    @Mock
    private GiftCertificateDAO certificateDao;

    @InjectMocks
    private GiftCertificateServiceImpl certificateService;

    @Test
    void getByIdTest() throws DaoException, ServiceException {
        GiftCertificate testCertificate = new GiftCertificate();
        testCertificate.setId(1);
        long badId = -1;

        ServiceException exception = assertThrows(ServiceException.class, () -> certificateService.getCertificateById(badId));
        assertEquals(ServiceExceptionCodes.BAD_ID, exception.getMessage());

        Mockito.when(certificateDao.getCertificateById(testCertificate.getId())).thenReturn(testCertificate);
        GiftCertificate returnedCertificate = certificateService.getCertificateById(testCertificate.getId());

        assertEquals(testCertificate, returnedCertificate);
    }

    @Test
    void getAllCertificatesTest() throws DaoException {
        List<GiftCertificate> expected = Arrays.asList(
                new GiftCertificate(),
                new GiftCertificate(),
                new GiftCertificate());

        Mockito.when(certificateDao.getAllCertificates()).thenReturn(expected);
        List<GiftCertificate> actual = certificateService.getAllCertificates();

        assertEquals(expected, actual);
    }

    @Test
    void removeByIdTest() {
        long badId = -1;

        ServiceException exception = assertThrows(ServiceException.class, () -> certificateService.removeCertificateById(badId));
        assertEquals(ServiceExceptionCodes.BAD_ID, exception.getMessage());
    }

    @Test
    void doFilterTest_CorrectParams() throws DaoException, ServiceException {
        List<GiftCertificate> expected = Arrays.asList(
                new GiftCertificate(),
                new GiftCertificate(),
                new GiftCertificate());


        Map<String, String> correctParameters = new LinkedHashMap<>();
        correctParameters.put(OptionParameter.NAME, "certificate");
        correctParameters.put(OptionParameter.SORT_BY_DATE, "ASC");
        correctParameters.put(OptionParameter.DESCRIPTION, "description");

        Mockito.when(certificateDao.getCertificatesWithParameters(correctParameters)).thenReturn(expected);

        List<GiftCertificate> actual = certificateService.getCertificatesWithParameters(correctParameters);

        assertEquals(expected, actual);

        Map<String, String> incorrectSortParameter = new LinkedHashMap<>();
        incorrectSortParameter.put(OptionParameter.NAME, "certificate");
        incorrectSortParameter.put(OptionParameter.SORT_BY_NAME, "some incorrect value");
        incorrectSortParameter.put(OptionParameter.TAG_NAME, "tagName");

        ServiceException exception = assertThrows(ServiceException.class,
                () -> certificateService.getCertificatesWithParameters(incorrectSortParameter));
        assertEquals(ServiceExceptionCodes.BAD_SORTING_TYPE, exception.getMessage());

        Map<String, String> incorrectKeyParameters = new LinkedHashMap<>();
        incorrectKeyParameters.put(OptionParameter.NAME, "certificate");
        incorrectKeyParameters.put(OptionParameter.SORT_BY_NAME, "ASC");
        incorrectKeyParameters.put("incorect key value", "incorrect value");

        exception = assertThrows(ServiceException.class,
                () -> certificateService.getCertificatesWithParameters(incorrectKeyParameters));
        assertEquals(ServiceExceptionCodes.BAD_GIFT_CERTIFICATE_FILTER_PARAMETER, exception.getMessage());
    }
}