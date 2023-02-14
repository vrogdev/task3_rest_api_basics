package com.epam.esm.dao.interfaces;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Map;

public interface TagDAO {

    List<Tag> getAllTags() throws DaoException;
    Tag getTagById(long id) throws DaoException;
    Tag getTagByName(String name) throws DaoException;

    void removeTagById(long id) throws DaoException;
    long insertTag(Tag item) throws DaoException;

    List<Tag> getTagsWithParameters(Map<String, String> params) throws DaoException;
}
