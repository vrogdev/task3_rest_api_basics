package com.epam.esm.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.List;

public abstract class AbstractDao<T> {
    protected final JdbcTemplate jdbcTemplate;
    protected final ResultSetExtractor<List<T>> resultSetExtractor;

    public AbstractDao(JdbcTemplate jdbcTemplate, ResultSetExtractor<List<T>> resultSetExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.resultSetExtractor = resultSetExtractor;
    }

    /**
     * Executes SQL query and returns only one {@link T} object.
     * @param query The SQL query that will be executed.
     * @param params The SQL query parameters.
     * @return An {@link T} object or <code>null</code> if no rows were returned.
     */
    public T executeQueryAsSingleEntity(String query, Object... params) {
        List<T> items = executeQuery(query, params);
        if (items == null || items.size() == 0) {
            return null;
        }
        return items.get(0);
    }

    /**
     * Executes SQL query and returns {@link List} of {@link T} objects.
     * @param query The SQL query that will be executed.
     * @param params The SQL query parameters.
     * @return A {@link List} of {@link T} objects or empty if no rows were returned.
     */
    public List<T> executeQuery(String query, Object... params) {
        return jdbcTemplate.query(query, resultSetExtractor, params);
    }

    public int executeUpdateQuery(String query, Object... params) {
        return jdbcTemplate.update(query, params);
    }
}
