package com.lid.outfitplannerbackend.DTOs;

import java.util.ArrayList;
import java.util.List;

public class OutfitDTO {
    private Integer id;
    private String name;
    private List<ClothingDTO> clothes = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ClothingDTO> getClothes() {
        return clothes;
    }

    public void setClothes(List<ClothingDTO> clothes) {
        this.clothes = clothes;
    }
}
