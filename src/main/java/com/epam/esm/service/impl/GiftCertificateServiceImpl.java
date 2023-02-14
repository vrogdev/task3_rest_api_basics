package com.epam.esm.service.impl;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.dao.interfaces.GiftCertificateDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.interfaces.GiftCertificateService;
import com.epam.esm.service.util.GiftCertificateValidator;
import com.epam.esm.service.util.OptionsParameterValidator;
import com.epam.esm.service.util.IdentifiableValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDAO certificateDao;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDAO certificateDao) {
        this.certificateDao = certificateDao;
    }

    @Override
    public List<GiftCertificate> getAllCertificates() throws DaoException {
        return certificateDao.getAllCertificates();
    }

    @Override
    public GiftCertificate getCertificateById(long id) throws DaoException, ServiceException {
        IdentifiableValidator.validateId(id);
        return certificateDao.getCertificateById(id);
    }

    @Override
    public void insertCertificate(GiftCertificate item) throws DaoException, ServiceException {
        String currentTimestamp = LocalDateTime.now().toString();
        item.setCreateDate(currentTimestamp);
        item.setLastUpdateDate(currentTimestamp);

        GiftCertificateValidator.validate(item);
        certificateDao.insertCertificate(item);
    }

    @Override
    public void removeCertificateById(long id) throws DaoException, ServiceException {
        IdentifiableValidator.validateId(id);
        certificateDao.removeCertificateById(id);
    }

    @Override
    public void updateCertificate(long id, GiftCertificate item) throws DaoException, ServiceException {
        String currentTimestamp = LocalDateTime.now().toString();
        item.setLastUpdateDate(currentTimestamp);

        IdentifiableValidator.validateId(id);
        GiftCertificateValidator.validateForUpdate(item);

        item.setId(id);
        certificateDao.updateCertificate(item);
    }

    @Override
    public List<GiftCertificate> getCertificatesWithParameters(Map<String, String> params) throws ServiceException, DaoException {
        OptionsParameterValidator.validateGiftCertificateRequestParameters(params);
        return certificateDao.getCertificatesWithParameters(params);
    }



}
