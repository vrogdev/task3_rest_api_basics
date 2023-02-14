package com.epam.esm.controller;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.interfaces.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/certificates")
public class GiftCertificateController {

    private final GiftCertificateService certificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping("/{id}")
    public GiftCertificate certificateById(@PathVariable long id) throws DaoException, ServiceException {
        return certificateService.getCertificateById(id);
    }

    @GetMapping
    public List<GiftCertificate> allCertificates() throws DaoException {
        return certificateService.getAllCertificates();
    }

    @PostMapping
    public ResponseEntity<String> createCertificate(@RequestBody GiftCertificate certificate) throws DaoException, ServiceException {
        certificateService.insertCertificate(certificate);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCertificateById(@PathVariable long id) throws DaoException, ServiceException {
        certificateService.removeCertificateById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Entity with id = " + id + "removed successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCertificate(@PathVariable long id,
                                                    @RequestBody GiftCertificate certificate) throws DaoException, ServiceException {
        certificateService.updateCertificate(id, certificate);

        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    @GetMapping("/filter")
    public List<GiftCertificate> certificateByParameters(@RequestParam Map<String, String> params) throws ServiceException, DaoException {
        return certificateService.getCertificatesWithParameters(params);
    }
}
