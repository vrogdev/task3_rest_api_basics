package com.epam.esm.service.impl;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.dao.interfaces.TagDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.interfaces.TagService;
import com.epam.esm.service.util.IdentifiableValidator;
import com.epam.esm.service.util.OptionsParameterValidator;
import com.epam.esm.service.util.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TagServiceImpl implements TagService {
    TagDAO dao;

    @Autowired
    public TagServiceImpl(TagDAO dao) {
        this.dao = dao;
    }

    @Override
    public Tag getTagById(long id) throws DaoException, ServiceException {
        IdentifiableValidator.validateId(id);
        return dao.getTagById(id);
    }

    @Override
    public List<Tag> getAllTags() throws DaoException {
        return dao.getAllTags();
    }

    @Override
    public void insertTag(Tag item) throws DaoException, ServiceException {
        TagValidator.validate(item);
        dao.insertTag(item);
    }

    @Override
    public void removeTagById(long id) throws DaoException, ServiceException {
        IdentifiableValidator.validateId(id);
        dao.removeTagById(id);
    }

    @Override
    public List<Tag> getTagsWithParameters(Map<String, String> params) throws ServiceException, DaoException {
        OptionsParameterValidator.validateTagParameters(params);
        return dao.getTagsWithParameters(params);
    }

}
