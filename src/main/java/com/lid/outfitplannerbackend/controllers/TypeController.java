package com.lid.outfitplannerbackend.controllers;

import com.lid.outfitplannerbackend.model.Type;
import com.lid.outfitplannerbackend.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class TypeController {

    private final TypeService typeService;

    @Autowired
    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping(value = "/clothes/types")
    public ResponseEntity getAll() {
        List<Type> typeList = typeService.getAll();
        if (!typeList.isEmpty()) {
            return new ResponseEntity<>(typeList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No type found!", HttpStatus.NOT_FOUND);
        }
    }
}
