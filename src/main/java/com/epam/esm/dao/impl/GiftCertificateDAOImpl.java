package com.epam.esm.dao.impl;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.dao.exception.DaoExceptionCodes;
import com.epam.esm.dao.interfaces.GiftCertificateDAO;
import com.epam.esm.dao.interfaces.TagDAO;
import com.epam.esm.dao.util.DaoUtils;
import com.epam.esm.dao.util.QueryBuilder;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GiftCertificateDAOImpl extends AbstractDao<GiftCertificate> implements GiftCertificateDAO {
    private static final String QUERY_SELECT_CERTIFICATE_ID =
            "SELECT gc.id FROM gift_certificate gc " +
                    "LEFT JOIN gift_certificate_has_tag gcht ON gc.id = gcht.gift_certificate_id " +
                    "LEFT JOIN tag t ON t.id = gcht.tag_id ";
    private static final String QUERY_SELECT_BY_ID =
            "SELECT * FROM gift_certificate gc " +
                    "LEFT JOIN gift_certificate_has_tag gcht ON gc.id = gcht.gift_certificate_id " +
                    "LEFT JOIN tag t ON t.id = gcht.tag_id WHERE gc.id = ?";
    private static final String QUERY_SELECT_ALL_CERTIFICATES =
            "SELECT * FROM gift_certificate gc " +
                    "LEFT JOIN gift_certificate_has_tag gcht ON gc.id = gcht.gift_certificate_id " +
                    "LEFT JOIN tag t ON t.id = gcht.tag_id";
    private static final String QUERY_DELETE_BY_ID =
            "DELETE FROM gift_certificate WHERE id = ?";
    private static final String QUERY_INSERT_NEW_TAGS_TO_CERTIFICATE =
            "INSERT INTO gift_certificate_has_tag VALUES (?, ?)";
    private static final String QUERY_INSERT_NEW_CERTIFICATE =
            "INSERT INTO gift_certificate(name, description, duration, create_date, last_update_date, price) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String QUERY_DELETE_ASSOCIATED_TAGS =
            "DELETE FROM gift_certificate_has_tag WHERE gift_certificate_id = ?";

    private static final String QUERY_UPDATE_CERTIFICATE_BASE_QUERY =
            "UPDATE gift_certificate SET ";


    private final TagDAO tagDao;

    @Autowired
    public GiftCertificateDAOImpl(JdbcTemplate jdbcTemplate,
                                  ResultSetExtractor<List<GiftCertificate>> resultSetExtractor,
                                  TagDAO tagDao) {
        super(jdbcTemplate, resultSetExtractor);

        this.tagDao = tagDao;
    }

    @Override
    public List<GiftCertificate> getAllCertificates() throws DaoException {
        List<GiftCertificate> items = executeQuery(QUERY_SELECT_ALL_CERTIFICATES);
        if (items.isEmpty()) {
            throw new DaoException(DaoExceptionCodes.NO_ENTITIES);
        }
        return items;
    }

    @Override
    public GiftCertificate getCertificateById(long id) throws DaoException {
        GiftCertificate item = executeQueryAsSingleEntity(QUERY_SELECT_BY_ID, id);

        if (item == null) {
            throw new DaoException(DaoExceptionCodes.NO_ENTITY_WITH_ID);
        }
        return item;
    }

    @Override
    @Transactional
    public void insertCertificate(GiftCertificate item) throws DaoException {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(
                            QUERY_INSERT_NEW_CERTIFICATE,
                            Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, item.getName());
                    ps.setString(2, item.getDescription());
                    ps.setInt(3, item.getDuration());
                    ps.setString(4, item.getCreateDate());
                    ps.setString(5, item.getLastUpdateDate());
                    ps.setDouble(6, item.getPrice());

                    return ps;
                },
                keyHolder
        );


        item.setId(DaoUtils.getNewCertificateId(keyHolder));

        if (item.getTags() != null) {
            addTagsToCertificate(item);
        }
    }

    private void addTagsToCertificate(GiftCertificate item) throws DaoException {
        List<Tag> newTags = setTagsWithId(item.getTags());
        for (Tag newTag : newTags) {
            executeUpdateQuery(
                    QUERY_INSERT_NEW_TAGS_TO_CERTIFICATE,
                    item.getId(),
                    newTag.getId()
            );
        }
    }

    private List<Tag> setTagsWithId(List<Tag> requestTags) throws DaoException {
        List<Tag> newTagsWithId = new ArrayList<>(requestTags.size());
        for (Tag requestTag : requestTags) {
            Tag tagWithId = null;

            try {
                tagWithId = tagDao.getTagByName(requestTag.getName());
            } catch (DaoException e) {
                long newTagId = tagDao.insertTag(requestTag);
                requestTag.setId(newTagId);
                tagWithId = requestTag;
            }

            newTagsWithId.add(tagWithId);
        }
        return newTagsWithId;
    }

    @Override
    public void removeCertificateById(long id) throws DaoException {
        int affectedRows = executeUpdateQuery(QUERY_DELETE_BY_ID, id);

        if (affectedRows == 0) {
            throw new DaoException(DaoExceptionCodes.NO_ENTITY_WITH_ID);
        }
    }

    @Override
    @Transactional
    public void updateCertificate(GiftCertificate item) throws DaoException {
        String updateQuery = QueryBuilder.buildQueryWithUpdateOptions(QUERY_UPDATE_CERTIFICATE_BASE_QUERY, item);

        int affectedRows = executeUpdateQuery(updateQuery);
        if (affectedRows == 0) {
            throw new DaoException(DaoExceptionCodes.NO_ENTITY_WITH_ID);
        }

        if (item.getTags() != null) {
            updateCertificateTags(item);
        }
    }

    @Override
    public List<GiftCertificate> getCertificatesWithParameters(Map<String, String> params) throws DaoException {
        String query = QueryBuilder.buildQueryWithParameterOptions(
                QUERY_SELECT_CERTIFICATE_ID,
                params);

        List<Integer> ids = jdbcTemplate.queryForList(query, Integer.class);

        if (ids.isEmpty()) {
            throw new DaoException(DaoExceptionCodes.NO_ENTITIES_WITH_PARAMETERS);
        }

        ids = ids.stream().distinct().collect(Collectors.toList());

        List<GiftCertificate> filteredCertificates = new ArrayList<>(ids.size());
        ids.forEach((id) -> {
            GiftCertificate item = executeQueryAsSingleEntity(
                    QUERY_SELECT_BY_ID,
                    id
            );
            filteredCertificates.add(item);
        });
        return filteredCertificates;
    }

    private void updateCertificateTags(GiftCertificate item) throws DaoException {
        List<Tag> newTags = setTagsWithId(item.getTags());

        executeUpdateQuery(
                QUERY_DELETE_ASSOCIATED_TAGS,
                item.getId()
        );

        newTags.forEach(newTag ->
                executeUpdateQuery(
                        QUERY_INSERT_NEW_TAGS_TO_CERTIFICATE,
                        item.getId(),
                        newTag.getId()
                )
        );
    }
}
