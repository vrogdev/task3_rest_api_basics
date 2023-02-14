package com.epam.esm.dao.util;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.dao.exception.DaoExceptionCodes;
import com.epam.esm.dao.util.constants.GiftCertificateDBColumn;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.dao.util.constants.OptionParameter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class QueryBuilder {

    public static String buildQueryWithUpdateOptions(String basicQuery, GiftCertificate item) {
        Map<String, String> fieldsToUpdate = getFieldsToUpdate(item);


        StringBuilder updateQuery = new StringBuilder(basicQuery);

        String id = fieldsToUpdate.get(GiftCertificateDBColumn.ID);
        fieldsToUpdate.remove(GiftCertificateDBColumn.ID);

        Set<Map.Entry<String, String>> entries = fieldsToUpdate.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            updateQuery.append(entry.getKey())
                    .append("=")
                    .append('\'').append(entry.getValue()).append('\'')
                    .append(", ");
        }
        updateQuery.deleteCharAt(updateQuery.length() - 2);
        updateQuery.append("where id=").append(id);

        return updateQuery.toString();
    }

    private static Map<String, String> getFieldsToUpdate(GiftCertificate item) {
        Map<String, String> fields = new HashMap<>();

        if (item.getId() != 0) {
            fields.put(GiftCertificateDBColumn.ID, String.valueOf(item.getId()));
        }

        if (item.getName() != null && !item.getName().isEmpty()) {
            fields.put(GiftCertificateDBColumn.NAME, item.getName());
        }

        if (item.getDescription() != null && !item.getDescription().isEmpty()) {
            fields.put(GiftCertificateDBColumn.DESCRIPTION, item.getDescription());
        }

        if (item.getPrice() != null) {
            fields.put(GiftCertificateDBColumn.PRICE, item.getPrice().toString());
        }

        if (item.getDuration() != 0) {
            fields.put(GiftCertificateDBColumn.DURATION, String.valueOf(item.getDuration()));
        }
        fields.put(GiftCertificateDBColumn.LAST_UPDATE_DATE, item.getLastUpdateDate());

        return fields;
    }

    public static String buildQueryWithParameterOptions(String basicQuery, Map<String, String> filterParams) throws DaoException {
        StringBuilder query = new StringBuilder(basicQuery);
        for (Map.Entry<String, String> entry : filterParams.entrySet()) {
            String param = entry.getKey().toLowerCase();

            switch (param) {
                case OptionParameter.NAME:
                    addPartParameter(query, "gc.name", entry.getValue());
                    break;
                case OptionParameter.DESCRIPTION:
                    addPartParameter(query, "gc.description", entry.getValue());
                    break;
                case OptionParameter.TAG_NAME: {
                    addPartParameter(query, "t.name", entry.getValue());
                    break;
                }
                case OptionParameter.SORT_BY_DATE: {
                    addSortParameter(query, "gc.create_date", entry.getValue());
                    break;
                }
                case OptionParameter.SORT_BY_NAME: {
                    addSortParameter(query, "gc.name", entry.getValue());
                    break;
                }
                case OptionParameter.SORT_BY_TAG_NAME: {
                    addSortParameter(query, "t.name", entry.getValue());
                    break;
                }
                default:
                    throw new DaoException(DaoExceptionCodes.NO_ENTITIES_WITH_PARAMETERS);
            }
        }
        return query.toString();
    }

    private static void addPartParameter(StringBuilder query, String param, String value) {
        if (query.toString().contains("WHERE")) {
            query.append(" AND ");
        } else {
            query.append(" WHERE ");
        }
        query.append(param).append(" LIKE '%").append(value).append("%' ");
    }

    private static void addSortParameter(StringBuilder query, String param, String value) {
        if (query.toString().contains("ORDER BY")) {
            query.append(", ");
        } else {
            query.append(" ORDER BY ");
        }
        query.append(param).append(" ").append(value);
    }
}
