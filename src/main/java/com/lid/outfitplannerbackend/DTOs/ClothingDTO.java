package com.lid.outfitplannerbackend.DTOs;

import com.lid.outfitplannerbackend.model.Category;
import com.lid.outfitplannerbackend.model.Color;
import com.lid.outfitplannerbackend.model.Type;

import java.util.ArrayList;
import java.util.List;

public class ClothingDTO {
    private int id;
    private String picture;
    private Type type;
    private List<Category> categories = new ArrayList<>();
    private List<Color> colors = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Color> getColors() {
        return colors;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }
}
