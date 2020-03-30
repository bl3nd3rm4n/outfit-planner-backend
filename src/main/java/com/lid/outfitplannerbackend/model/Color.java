package com.lid.outfitplannerbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "colors")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Color implements Comparable<Color> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String centerRgb;
    @Column
    private double endHsv;

    public Color() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCenterRgb() {
        return centerRgb;
    }

    public void setCenterRgb(String centerRgb) {
        this.centerRgb = centerRgb;
    }

    public double getEndHsv() {
        return endHsv;
    }

    public void setEndHsv(double endHsv) {
        this.endHsv = endHsv;
    }

    @Override
    public String toString() {
        return "Color{" + name + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return id == color.id &&
                Double.compare(color.endHsv, endHsv) == 0 &&
                Objects.equals(name, color.name) &&
                Objects.equals(centerRgb, color.centerRgb);
    }

    @Override
    public int compareTo(Color o) {
        return Double.compare(this.endHsv, o.endHsv);
    }
}

