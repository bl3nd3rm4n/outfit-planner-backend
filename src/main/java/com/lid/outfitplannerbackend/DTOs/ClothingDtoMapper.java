package com.lid.outfitplannerbackend.DTOs;

import com.lid.outfitplannerbackend.model.Clothing;

public class ClothingDtoMapper {

    public static ClothingDTO entityToDto(Clothing clothing) {
        ClothingDTO clothingDTO = new ClothingDTO();
        clothingDTO.setId(clothing.getId());
        String myPicture = new String(clothing.getPicture());
        clothingDTO.setPicture(myPicture);
        clothingDTO.setCategories(clothing.getCategories());
        clothingDTO.setColors(clothing.getColors());
        clothingDTO.setType(clothing.getType());
        return clothingDTO;
    }

    public static Clothing dtoToEntity(ClothingDTO clothingDTO) {
        Clothing clothing = new Clothing();
        clothing.setId(clothingDTO.getId());
        byte[] myPicture = clothingDTO.getPicture().getBytes();
        clothing.setPicture(myPicture);
        clothing.setCategories(clothingDTO.getCategories());
        clothing.setColors(clothingDTO.getColors());
        clothing.setType(clothingDTO.getType());
        return clothing;
    }
}
