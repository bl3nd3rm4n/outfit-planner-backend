package com.lid.outfitplannerbackend.controllers;

import com.lid.outfitplannerbackend.DTOs.ClothingDTO;
import com.lid.outfitplannerbackend.DTOs.ClothingDtoMapper;
import com.lid.outfitplannerbackend.model.Clothing;
import com.lid.outfitplannerbackend.model.Color;
import com.lid.outfitplannerbackend.services.ClothingService;
import com.lid.outfitplannerbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class ClothingController {

    private final ClothingService clothingService;

    private final UserService userService;

    @Autowired
    public ClothingController(ClothingService clothingService, UserService userService) {
        this.clothingService = clothingService;
        this.userService = userService;
    }

    @GetMapping(value = "users/{userId}/clothes")
    public ResponseEntity getAll(@PathVariable int userId) {
        List<ClothingDTO> clothes = userService.getById(userId).getClothes().stream()
                .map(ClothingDtoMapper::entityToDto)
                .collect(Collectors.toList());
        if (!clothes.isEmpty()) {
            return new ResponseEntity<>(clothes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No clothes found!", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "users/{userId}/clothes/{id}")
    public ResponseEntity getById(@PathVariable int id, @PathVariable int userId) {
        Clothing clothing = clothingService.getById(id);
        if (clothing != null) {
            return new ResponseEntity<>(ClothingDtoMapper.entityToDto(clothing), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Clothing not found!", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "users/{userId}/clothes")
    public ResponseEntity save(@RequestBody ClothingDTO clothingDTO, @PathVariable int userId) {
        try {
            Clothing clothing = clothingService.insert(ClothingDtoMapper.dtoToEntity(clothingDTO));
            clothing = userService.insertClothing(userId, clothing);
            return new ResponseEntity<>(clothing, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Clothing could not be added to user!", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "users/{userId}/clothes/{clothingId}")
    public ResponseEntity update(@RequestBody ClothingDTO clothingDTO, @PathVariable int userId, @PathVariable int clothingId) {
        try {
            Clothing clothing = clothingService.update(ClothingDtoMapper.dtoToEntity(clothingDTO));
            return new ResponseEntity<>(clothing, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Clothing could not be updated!", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "clothes/colors/{id}")
    public ResponseEntity getColors(@PathVariable int id) {
        List<Color> colors = clothingService.distinguishColors(clothingService.getById(id));
        if (!colors.isEmpty()) {
            return new ResponseEntity<>(colors, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No colors found!", HttpStatus.NOT_FOUND);
        }
    }
}
