package com.epam.esm.dao.impl;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.dao.exception.DaoExceptionCodes;
import com.epam.esm.dao.extractor.TagExtractor;
import com.epam.esm.dao.interfaces.TagDAO;
import com.epam.esm.dao.util.DaoUtils;
import com.epam.esm.dao.util.QueryBuilder;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Component
public class TagDAOImpl extends AbstractDao<Tag> implements TagDAO {
    private static final String QUERY_SELECT_ALL_TAGS = "SELECT * FROM tag";
    private static final String QUERY_SELECT_BY_ID = "SELECT * FROM tag WHERE id = ?";
    private static final String QUERY_SELECT_BY_NAME = "SELECT * FROM tag WHERE name LIKE ?";
    private static final String QUERY_INSERT_TAG = "INSERT INTO tag(name) VALUES (?)";
    private static final String QUERY_DELETE_BY_ID = "DELETE FROM tag WHERE id = ?";
    private static final String QUERY_SELECT_WITH_OPTIONS = "SELECT * FROM tag t";

    @Autowired
    public TagDAOImpl(JdbcTemplate jdbcTemplate, TagExtractor tagExtractor) {
        super(jdbcTemplate, tagExtractor);
    }

    @Override
    public List<Tag> getAllTags() throws DaoException {
        List<Tag> items = executeQuery(QUERY_SELECT_ALL_TAGS);
        return items;
    }

    @Override
    public Tag getTagById(long id) throws DaoException {
        Tag item = executeQueryAsSingleEntity(QUERY_SELECT_BY_ID, id);
        if (item == null) {
            throw new DaoException(DaoExceptionCodes.NO_ENTITY_WITH_ID);
        }
        return item;
    }

    @Override
    public Tag getTagByName(String name) throws DaoException {
        Tag item = executeQueryAsSingleEntity(QUERY_SELECT_BY_NAME, name);

        if (item == null) {
            throw new DaoException(DaoExceptionCodes.NO_ENTITY_WITH_NAME);
        }

        return item;
    }

    @Override
    public void removeTagById(long id) throws DaoException {
        int affectedRows = executeUpdateQuery(QUERY_DELETE_BY_ID, id);
        if (affectedRows == 0) {
            throw new DaoException(DaoExceptionCodes.NO_ENTITY_WITH_ID);
        }
    }

    @Override
    public long insertTag(Tag item) throws DaoException {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(
                            QUERY_INSERT_TAG,
                            Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, item.getName());
                    return ps;
                },
                keyHolder
        );

        return DaoUtils.getNewCertificateId(keyHolder);
    }

    @Override
    public List<Tag> getTagsWithParameters(Map<String, String> params) throws DaoException {
        String getQuery = QueryBuilder.buildQueryWithParameterOptions(QUERY_SELECT_WITH_OPTIONS, params);
        List<Tag> filteredTags = executeQuery(getQuery);
        if (filteredTags.isEmpty()) {
            throw new DaoException(DaoExceptionCodes.NO_ENTITIES_WITH_PARAMETERS);
        }
        return filteredTags;
    }
}
