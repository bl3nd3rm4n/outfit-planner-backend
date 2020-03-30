package com.lid.outfitplannerbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "clothes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Clothing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @ManyToOne
    @JoinColumn(name = "typeid", nullable = false)
    private Type type;

    @ManyToMany
    @JoinTable(name = "clothes_categories", joinColumns = {@JoinColumn(name = "clothingid")}, inverseJoinColumns = {@JoinColumn(name = "categoryid")})
    private List<Category> categories = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "clothes_colors", joinColumns = {@JoinColumn(name = "clothingid")}, inverseJoinColumns = {@JoinColumn(name = "colorid")})
    private List<Color> colors = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
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

    @Override
    public String toString() {
//        return "Clothing{" +
//                "id=" + id +
//                ", type=" + type +
//                ", categories=" + categories +
//                ", colors=" + colors +
//                '}';
        return "((" + categories + colors + "))";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothing clothing = (Clothing) o;
        return Objects.equals(id, clothing.id) &&
                Arrays.equals(picture, clothing.picture) &&
                Objects.equals(type, clothing.type) &&
                Objects.equals(categories, clothing.categories) &&
                Objects.equals(colors, clothing.colors);
    }
}
