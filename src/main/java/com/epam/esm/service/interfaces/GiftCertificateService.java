package com.epam.esm.service.interfaces;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface GiftCertificateService {
    List<GiftCertificate> getAllCertificates() throws DaoException;
    GiftCertificate getCertificateById(long id) throws DaoException, ServiceException;
    void insertCertificate(GiftCertificate item) throws DaoException, ServiceException;
    void removeCertificateById(long id) throws DaoException, ServiceException;
    void updateCertificate(long id, GiftCertificate item) throws DaoException, ServiceException;
    List<GiftCertificate> getCertificatesWithParameters(Map<String, String> params) throws ServiceException, DaoException;
}
