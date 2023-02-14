package com.epam.esm.controller;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.interfaces.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }


    @GetMapping
    public List<Tag> allTags() throws DaoException {
        return tagService.getAllTags();
    }

    @GetMapping("/{id}")
    public Tag tagById(@PathVariable long id) throws DaoException, ServiceException {
        return tagService.getTagById(id);
    }

    @PostMapping
    public ResponseEntity<String> createTag(@RequestBody Tag tag) throws DaoException, ServiceException {
        tagService.insertTag(tag);
        return ResponseEntity.status(HttpStatus.CREATED).body("Tag has been successfully added");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id) throws DaoException, ServiceException {
        tagService.removeTagById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Tag with id = "+id+" successfully deleted");
    }

    @GetMapping("/filter")
    public List<Tag> tagByParameters(@RequestParam Map<String, String> params) throws ServiceException, DaoException {
        return tagService.getTagsWithParameters(params);
    }

}
