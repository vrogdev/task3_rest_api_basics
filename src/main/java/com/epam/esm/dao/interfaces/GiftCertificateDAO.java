package com.epam.esm.dao.interfaces;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDAO {
    List<GiftCertificate> getAllCertificates() throws DaoException;
    GiftCertificate getCertificateById(long id) throws DaoException;
    void insertCertificate(GiftCertificate item) throws DaoException;
    void removeCertificateById(long id) throws DaoException;
    void updateCertificate(GiftCertificate item) throws DaoException;

    List<GiftCertificate> getCertificatesWithParameters(Map<String, String> params) throws DaoException;
}
