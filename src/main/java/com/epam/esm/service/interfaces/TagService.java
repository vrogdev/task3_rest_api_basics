package com.epam.esm.service.interfaces;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface TagService {
    List<Tag> getAllTags() throws DaoException;
    Tag getTagById(long id) throws DaoException, ServiceException;
    void insertTag(Tag item) throws DaoException, ServiceException;
    void removeTagById(long id) throws DaoException, ServiceException;
    List<Tag> getTagsWithParameters(Map<String, String> params) throws ServiceException, DaoException;
}
