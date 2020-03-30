package com.lid.outfitplannerbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "outfits")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Outfit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column
    private String name;
    @ManyToMany
    @JoinTable(
            name = "outfits_clothes",
            joinColumns = {@JoinColumn(name = "outfitid")},
            inverseJoinColumns = {@JoinColumn(name = "clothingid")}
    )
    private List<Clothing> clothes = new ArrayList<>();
    public Outfit() {
    }

    public Outfit(List<Clothing> clothes) {
        this.clothes = clothes;
    }

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

    public List<Clothing> getClothes() {
        return clothes;
    }

    public void setClothes(List<Clothing> clothes) {
        this.clothes = clothes;
    }

    @Override
    public String toString() {
        return "Outfit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", clothes=" + clothes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Outfit outfit = (Outfit) o;
        return Objects.equals(id, outfit.id) &&
                Objects.equals(name, outfit.name) &&
                Objects.equals(clothes, outfit.clothes);
    }
}
