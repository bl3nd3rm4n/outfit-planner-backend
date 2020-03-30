package com.lid.outfitplannerbackend.services;

import com.lid.outfitplannerbackend.model.Color;
import com.lid.outfitplannerbackend.persistence.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ColorService implements IService<Color> {

    private final ColorRepository colorRepository;

    @Autowired
    public ColorService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Transactional
    @Override
    public List<Color> getAll() {
        return colorRepository.findAll();
    }

    @Transactional
    @Override
    public Color getById(int id) {
        return colorRepository.getOne(id);
    }

    /*
        nr: 1 = monochromatic
            2 = complementary
            3 = triad
            4 = square
     */
    public List<Color> getGeometricMatchingColors(Color color, int nr) {
        List<Color> colors = getAll();
        Collections.sort(colors);
        List<Color> matchingColors = new ArrayList<>();
        matchingColors.add(colors.remove(14));
        matchingColors.add(colors.remove(13));
        matchingColors.add(colors.remove(12));
        int indexOfRequestedColor = colors.indexOf(color);
        for (int i = 0; i < nr; i++) {
            matchingColors.add(colors.get((indexOfRequestedColor + i * (12 / nr)) % 12));
        }
        return matchingColors;
    }

    public List<Color> getAnalogousMatchingColors(Color color) {
        List<Color> colors = getAll();
        Collections.sort(colors);
        List<Color> matchingColors = new ArrayList<>();
        matchingColors.add(colors.remove(14));
        matchingColors.add(colors.remove(13));
        matchingColors.add(colors.remove(12));
        int indexOfRequestedColor = colors.indexOf(color);
        matchingColors.add(colors.get((indexOfRequestedColor + 11) % 12));
        matchingColors.add(color);
        matchingColors.add(colors.get((indexOfRequestedColor + 1) % 12));
        return matchingColors;
    }

    public List<Color> getSplitComplementaryMatchingColors(Color color) {
        List<Color> colors = getAll();
        Collections.sort(colors);
        List<Color> matchingColors = new ArrayList<>();
        matchingColors.add(colors.remove(14));
        matchingColors.add(colors.remove(13));
        matchingColors.add(colors.remove(12));
        int indexOfRequestedColor = colors.indexOf(color);
        matchingColors.add(color);
        matchingColors.add(colors.get((indexOfRequestedColor + 5) % 12));
        matchingColors.add(colors.get((indexOfRequestedColor + 7) % 12));
        return matchingColors;
    }

    public List<List<Color>> getAllMatchingCombinations(Color color) {
        List<List<Color>> colorCombinations = new ArrayList<>();
        colorCombinations.add(getGeometricMatchingColors(color, 1));
        colorCombinations.add(getGeometricMatchingColors(color, 3));
        colorCombinations.add(getGeometricMatchingColors(color, 4));
        colorCombinations.add(getAnalogousMatchingColors(color));
        colorCombinations.add(getSplitComplementaryMatchingColors(color));
        return colorCombinations;
    }
}
