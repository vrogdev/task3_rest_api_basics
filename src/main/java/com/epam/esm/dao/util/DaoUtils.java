package com.epam.esm.dao.util;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.dao.exception.DaoExceptionCodes;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Optional;

/**
 * Util help to get newly created id from {@link KeyHolder} object
 */
public class DaoUtils {
    /**
     * Retrieves an created id from {@link KeyHolder} object.
     *
     * @param keyHolder
     * @return id from {@link KeyHolder}
     * @throws DaoException if id not found in given KeyHolder
     */
    public static long getNewCertificateId(KeyHolder keyHolder) throws DaoException {
        Number newId = Optional.ofNullable(keyHolder.getKey()).orElseThrow(() -> new DaoException(DaoExceptionCodes.SAVING_ERROR));
        return newId.longValue();
    }
}
