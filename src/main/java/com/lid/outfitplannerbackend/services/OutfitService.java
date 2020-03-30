package com.lid.outfitplannerbackend.services;

import com.lid.outfitplannerbackend.model.Category;
import com.lid.outfitplannerbackend.model.Clothing;
import com.lid.outfitplannerbackend.model.Color;
import com.lid.outfitplannerbackend.model.Outfit;
import com.lid.outfitplannerbackend.persistence.OutfitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class OutfitService implements IService<Outfit> {

    private static Map<String, String> logicTypes = new HashMap<String, String>() {{
        put("Jacket", "Top2");
        put("T-Shirt", "Top1");
        put("Shirt", "Top1");
        put("Pants", "Bottom");
        put("Skirt", "Bottom");
        put("Shorts", "Bottom");
    }};

    private final OutfitRepository outfitRepository;
    private final ClothingService clothingService;
    private final ColorService colorService;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public OutfitService(OutfitRepository outfitRepository, ClothingService clothingService, ColorService colorService, UserService userService, CategoryService categoryService) {
        this.outfitRepository = outfitRepository;
        this.clothingService = clothingService;
        this.colorService = colorService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Transactional
    @Override
    public List<Outfit> getAll() {
        return outfitRepository.findAll();
    }

    @Transactional
    @Override
    public Outfit getById(int id) {
        return outfitRepository.getOne(id);
    }

    public Outfit insert(Outfit outfit) {
        return outfitRepository.save(outfit);
    }

    public Set<Outfit> generateOutfits(int userId, int idClothing, int categoryId) {
        Clothing clothing = clothingService.getById(idClothing);
        Category category = categoryService.getById(categoryId);
        Set<Outfit> outfits = new HashSet<>();
        List<Color> colors = clothing.getColors();
        Color firstColor = getFirstColor(colors);
        System.out.println(category);
        System.out.println(firstColor);
        List<List<Color>> colorHarmonies = colorService.getAllMatchingCombinations(firstColor);
        ArrayList<Clothing> allClothesBackup = userService.getById(userId).getClothes().stream()
                .filter(x -> !logicTypes.get(x.getType().getName()).equals(logicTypes.get(clothing.getType().getName()))).collect(Collectors.toCollection(ArrayList::new));
        for (List<Color> colorHarmony : colorHarmonies) {
            List<Clothing> allClothes = getMatchingClothing(category, allClothesBackup, colorHarmony);
            for (int i = 0; i < allClothes.size(); i++) {
                List<Clothing> clothesInOutfit = Arrays.asList(clothing, allClothes.get(i));
                if (category.getName().equals("Winter") && allClothes.size() >= 2) {
                    for (int j = i; j < allClothes.size(); j++) {
                        if (!logicTypes.get(allClothes.get(j).getType().getName()).equals(logicTypes.get(allClothes.get(i).getType().getName()))) {
                            clothesInOutfit.add(allClothes.get(j));
                        }
                    }
                }
                if (isOutfitComplete(category, clothesInOutfit)) {
                    outfits.add(new Outfit(clothesInOutfit));
                }
            }
        }
        for (Outfit outfit : outfits) {
            System.out.println(outfit);
//            outfit = insert(outfit);
//            userService.insertOutfit(userId, outfit);
        }
        return outfits;
    }

    private boolean isOutfitComplete(Category category, List<Clothing> clothesInOutfit) {
        return category.getName().equals("Winter") && clothesInOutfit.size() == 3 ||
                !category.getName().equals("Winter") && clothesInOutfit.size() == 2;
    }

    private List<Clothing> getMatchingClothing(Category category, ArrayList<Clothing> allClothesBackup, List<Color> colorHarmony) {
        List<Clothing> allClothes = new ArrayList<>();
        System.out.println(allClothesBackup);
        for (Clothing clothing : allClothesBackup) {
            if (clothing.getCategories().stream().map(Category::getName).collect(Collectors.toList()).contains(category.getName()) && colorHarmony.contains(getFirstColor(clothing.getColors()))) {
                allClothes.add(clothing);
            }
        }
        System.out.println("All clothes after filtering " + allClothes);
        System.out.println("Colors in harmony " + colorHarmony);
        return allClothes;
    }

    private Color getFirstColor(List<Color> colors) {
        Color firstColor = null;
        for (Color color : colors) {
            if (!color.getName().equals("white") && !color.getName().equals("gray") && !color.getName().equals("black")) {
                firstColor = color;
                break;
            }
        }
        if (firstColor == null) {
            firstColor = colors.get(0);
        }
        return firstColor;
    }
}
