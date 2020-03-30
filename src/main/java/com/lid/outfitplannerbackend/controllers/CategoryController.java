package com.lid.outfitplannerbackend.controllers;

import com.lid.outfitplannerbackend.model.Category;
import com.lid.outfitplannerbackend.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/clothes/categories")
    public ResponseEntity getAll() {
        List<Category> categoryList = categoryService.getAll();
        if (!categoryList.isEmpty()) {
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No category found!", HttpStatus.NOT_FOUND);
        }
    }
}
