package com.epam.esm.service.impl;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.dao.interfaces.TagDAO;
import com.epam.esm.dao.util.constants.OptionParameter;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.ServiceExceptionCodes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagDAO tagDao;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void getTagById() throws DaoException, ServiceException {
        ServiceException exception = assertThrows(ServiceException.class, () -> tagService.getTagById(-1));
        assertEquals(ServiceExceptionCodes.BAD_ID, exception.getMessage());

        Tag tag = new Tag(1, "tag1");

        Mockito.when(tagDao.getTagById(tag.getId())).thenReturn(tag);
        Tag actual = tagService.getTagById(tag.getId());
        assertEquals(tag, actual);


    }

    @Test
    void getAllTags() throws DaoException {
        List<Tag> expected = Arrays.asList(
                new Tag(1, "tag1"),
                new Tag(2, "tag2"),
                new Tag(3, "tag3"));

        Mockito.when(tagDao.getAllTags()).thenReturn(expected);
        List<Tag> actual = tagService.getAllTags();
        assertEquals(expected, actual);
    }

    @Test
    void getTagsWithParameters() throws DaoException, ServiceException {
        Tag tag = new Tag(1, "tag1");
        List<Tag> expected = Arrays.asList(tag);

        Map<String, String> params = new HashMap<>();
        params.put(OptionParameter.SORT_BY_TAG_NAME, "ASC");
        params.put(OptionParameter.TAG_NAME, tag.getName());


        Mockito.when(tagDao.getTagsWithParameters(params)).thenReturn(expected);
        List<Tag> actual = tagService.getTagsWithParameters(params);

        assertEquals(expected, actual);
    }
}