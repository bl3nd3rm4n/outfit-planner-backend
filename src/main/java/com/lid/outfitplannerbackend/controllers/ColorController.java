package com.lid.outfitplannerbackend.controllers;

import com.lid.outfitplannerbackend.model.Color;
import com.lid.outfitplannerbackend.services.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class ColorController {

    private final ColorService colorService;

    @Autowired
    public ColorController(ColorService colorService) {
        this.colorService = colorService;
    }

    @GetMapping(value = "/clothes/colors")
    public ResponseEntity getAll() {
        List<Color> colorList = colorService.getAll();
        if (!colorList.isEmpty()) {
            return new ResponseEntity<>(colorList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No color found!", HttpStatus.NOT_FOUND);
        }
    }
}
