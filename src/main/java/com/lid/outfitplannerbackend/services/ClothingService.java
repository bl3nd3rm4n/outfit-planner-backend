package com.lid.outfitplannerbackend.services;

import com.lid.outfitplannerbackend.model.Clothing;
import com.lid.outfitplannerbackend.model.Color;
import com.lid.outfitplannerbackend.persistence.ClothingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ClothingService implements IService<Clothing> {

    private final ClothingRepository clothingRepository;
    private final ColorService colorService;

    @Autowired
    public ClothingService(ClothingRepository clothingRepository, ColorService colorService) {
        this.clothingRepository = clothingRepository;
        this.colorService = colorService;
    }

    @Transactional
    @Override
    public List<Clothing> getAll() {
        return clothingRepository.findAll();
    }

    @Transactional
    @Override
    public Clothing getById(int id) {
        return clothingRepository.getOne(id);
    }

    public Clothing insert(Clothing clothing) {
        clothing.setColors(distinguishColors(clothing));
        return clothingRepository.save(clothing);
    }

    public Clothing update(Clothing clothing) {
        return clothingRepository.save(clothing);
    }

    public List<Color> distinguishColors(Clothing clothing) {
        List<Color> colors = colorService.getAll();
        Collections.sort(colors);
        Map<Color, Integer> colorFrequency = new HashMap<>();
        for (Color color : colors) {
            colorFrequency.put(color, 0);
        }
        String imageData = new String(clothing.getPicture());
        String base64Data = imageData.split(",")[1];
        byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
        ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
        int threshHold = 0;
        try {
            BufferedImage image = ImageIO.read(bis);
            threshHold = (int) (image.getHeight() * image.getWidth() * 0.01);
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    float[] hsb = new float[3];
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = (rgb) & 0xFF;
                    java.awt.Color.RGBtoHSB(r, g, b, hsb);
                    if (hsb[2] < 0.1) {
                        colorFrequency.put(colors.get(14), colorFrequency.get(colors.get(14)) + 1);//black
                    } else if (hsb[2] > 0.9 && hsb[1] < 0.1) {
                        colorFrequency.put(colors.get(12), colorFrequency.get(colors.get(12)) + 1);//white
                    } else if (hsb[1] < 0.1) {
                        colorFrequency.put(colors.get(13), colorFrequency.get(colors.get(13)) + 1);//gray
                    } else {
                        double start = 0;
                        float deg = hsb[0] * 360;
                        boolean found = false;
                        for (Color color : colors.subList(0, 12)) {
                            if (deg >= start && deg < color.getEndHsv()) {
                                colorFrequency.put(color, colorFrequency.get(color) + 1);
                                found = true;
                                break;
                            }
                            start = color.getEndHsv();
                        }
                        if (!found) {
                            colorFrequency.put(colors.get(0), colorFrequency.get(colors.get(0)) + 1);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Color color : colors) {
            System.out.println(color.getName() + ": " + colorFrequency.get(color));
        }
        int limit = threshHold;
        return colorFrequency.entrySet().stream().filter(x -> x.getValue() > limit).sorted(Comparator.comparing(Map.Entry::getValue)).map(x -> x.getKey()).collect(Collectors.toList());
    }
}
