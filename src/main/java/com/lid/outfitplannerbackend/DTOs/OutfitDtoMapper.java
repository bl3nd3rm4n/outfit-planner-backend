package com.lid.outfitplannerbackend.DTOs;

import com.lid.outfitplannerbackend.model.Outfit;

import java.util.stream.Collectors;

public class OutfitDtoMapper {
    public static OutfitDTO entityToDto(Outfit outfit) {
        OutfitDTO outfitDTO = new OutfitDTO();
        outfitDTO.setId(outfit.getId());
        outfitDTO.setName(outfit.getName());
        outfitDTO.setClothes(outfit.getClothes().stream().map(ClothingDtoMapper::entityToDto)
                .collect(Collectors.toList()));
        return outfitDTO;
    }

    public static Outfit dtoToEntity(OutfitDTO outfitDTO) {
        Outfit outfit = new Outfit();
        outfit.setId(outfitDTO.getId());
        outfit.setName(outfitDTO.getName());
        outfit.setClothes(outfitDTO.getClothes().stream().map(ClothingDtoMapper::dtoToEntity)
                .collect(Collectors.toList()));
        return outfit;
    }
}
