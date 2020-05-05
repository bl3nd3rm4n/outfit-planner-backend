package com.lid.outfitplannerbackend;

import com.lid.outfitplannerbackend.model.*;
import com.lid.outfitplannerbackend.persistence.*;
import com.lid.outfitplannerbackend.services.ClothingService;
import com.lid.outfitplannerbackend.services.OutfitService;
import com.lid.outfitplannerbackend.services.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.sql.Date;
import java.util.Base64;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("main.java.persistence.*")
public class OutfitPlannerBackendApplicationTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ClothingService clothingService;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ClothingRepository clothingRepository;

    @Autowired
    private OutfitService outfitService = new OutfitService(null,null,null,null,null);

    @Test
    public void contextLoads() {
    }

    public byte[] concatenate(byte[] a, byte[] b) {
        int aLen = a.length;
        int bLen = b.length;

        @SuppressWarnings("unchecked")
        byte[] c = (byte[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);

        return c;
    }

    private void addColors() {
        colorRepository.save(new Color("red", "#ff0000", 0));
        colorRepository.save(new Color("red_orange", "#ff4000", 15));
        colorRepository.save(new Color("orange", " #ff8000", 30));
        colorRepository.save(new Color("amber", " #ffbf00", 45));
        colorRepository.save(new Color("yellow", " #ffff00", 60));
        colorRepository.save(new Color("lime", "#80ff00", 90));
        colorRepository.save(new Color("green", "#00ff00", 120));
        colorRepository.save(new Color("cyan", "#00ffff", 180));
        colorRepository.save(new Color("blue", "#0000ff", 240));
        colorRepository.save(new Color("purple", "#8000ff", 270));
        colorRepository.save(new Color("magenta", "#ff00ff", 300));
        colorRepository.save(new Color("pink", "#ff0080", 330));
        colorRepository.save(new Color("white", "#FFFFFF", 420));
        colorRepository.save(new Color("grey", "#77777", 420));
        colorRepository.save(new Color("black", "#000000", 420));
        colorRepository.flush();
    }

    private void addTypes() {
        typeRepository.save(new Type("top"));
        typeRepository.save(new Type("bottom"));
        typeRepository.flush();
    }

    private void addCategories() {
        categoryRepository.save(new Category("winter"));
        categoryRepository.save(new Category("summer"));
        categoryRepository.flush();
    }

    private void createClothing(Integer userId, String picturePath, List<Category> categories, Type type) throws IOException {
        String prefix = "data:image/png;base64,";

        Clothing clothing = new Clothing();
        clothing.setCategories(categories);
        clothing.setType(type);

        File picture = new File(picturePath);
        byte[] bytes = Files.readAllBytes(picture.toPath());
        clothing.setPicture(concatenate(prefix.getBytes(), Base64.getEncoder().encode(bytes)));

        userService.insertClothing(userId, clothingService.insert(clothing));
    }

    private void addClothes(Integer userId) throws IOException {
        String pathTop1 = "src\\test\\resources\\tops\\hmgoepprod1.jpg";
        String pathTop2 = "src\\test\\resources\\tops\\hmgoepprod2.jpg";
        String pathTop3 = "src\\test\\resources\\tops\\hmgoepprod3.jpg";
        String pathPants1 = "src\\test\\resources\\pants\\hmgoepprod1.jpg";
        String pathPants2 = "src\\test\\resources\\pants\\hmgoepprod2.jpg";
        String pathPants3 = "src\\test\\resources\\pants\\hmgoepprod3.jpg";

        createClothing(userId, pathTop1, singletonList(categoryRepository.findAll().get(0)), typeRepository.findAll().get(0));
        createClothing(userId, pathTop2, singletonList(categoryRepository.findAll().get(0)), typeRepository.findAll().get(0));
        createClothing(userId, pathTop3, singletonList(categoryRepository.findAll().get(0)), typeRepository.findAll().get(0));
        createClothing(userId, pathPants1, categoryRepository.findAll(), typeRepository.findAll().get(1));
        createClothing(userId, pathPants2, categoryRepository.findAll(), typeRepository.findAll().get(1));
        createClothing(userId, pathPants3, categoryRepository.findAll(), typeRepository.findAll().get(1));
    }

    @Before
    public void addTestData() throws IOException {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setLastLogin(Date.valueOf("2020-03-03"));
        userRepository.saveAndFlush(user);
        user = userService.login(user.getUsername(), user.getPassword());
        addColors();
        addCategories();
        addTypes();
        addClothes(user.getUserId());
    }

    @Test
    public void loginTest() {
        assertNotNull(userService.login("test", "test"));
        assertNull(userService.login("fail", "test"));
    }

    @Test
    public void registerTest() {
        assertNull(userService.register("test", "test"));
        assertNotNull(userService.register("newtest", "test"));
    }

    @Test
    public void testInvalidDate() {
        assertThrows(IllegalArgumentException.class, () -> userRepository.getAllByLastLoginBetween(Date.valueOf("banane"), Date.valueOf("")));
        assertThrows(IllegalArgumentException.class, () -> userRepository.getAllByLastLoginBetween(Date.valueOf("1.1.1"), null));
    }

    @Test
    public void invalidBoundaryTest() {
        assertThrows(IllegalArgumentException.class, () -> userRepository.getAllByLastLoginBetween(Date.valueOf("2019-13-13"), Date.valueOf("2019-13-13")));
        assertThrows(IllegalArgumentException.class, () -> userRepository.getAllByLastLoginBetween(Date.valueOf("2020-00-00"), Date.valueOf("2020-00-00")));
    }

    @Test
    public void validBoundaryTest() {
        assertFalse(userRepository.getAllByLastLoginBetween(Date.valueOf("2020-01-01"), Date.valueOf("2020-05-01")).isEmpty());
        assertTrue(userRepository.getAllByLastLoginBetween(Date.valueOf("9999-12-31"), Date.valueOf("9999-12-31")).isEmpty());
    }

    @Test
    public void datePartition() {
        assertThrows(IllegalArgumentException.class, () -> userRepository.getAllByLastLoginBetween(Date.valueOf("String care nu reprezinta o data"), Date.valueOf("9999-12-31")));
        assertThrows(IllegalArgumentException.class, () -> userRepository.getAllByLastLoginBetween(Date.valueOf("333-01-01"), Date.valueOf("9999-12-31")));
        assertThrows(IllegalArgumentException.class, () -> userRepository.getAllByLastLoginBetween(Date.valueOf("55555-01-01"), Date.valueOf("9999-12-31")));
        assertTrue(userRepository.getAllByLastLoginBetween(Date.valueOf("9998-01-01"), Date.valueOf("9999-12-31")).isEmpty());
    }

    @Test
    public void filterTest() {
        assertEquals(3, clothingRepository.findAllByType(typeRepository.findAll().get(0)).size());
        assertEquals(3, clothingRepository.findAllByType(typeRepository.findAll().get(1)).size());
        assertEquals(clothingRepository.count(),
                clothingRepository.findAllByCategoriesContains(categoryRepository.findAll().get(0)).size());
        assertEquals(3, clothingRepository.findAllByCategoriesContains(categoryRepository.findAll().get(1)).size());
    }

    @Test
    public void integrationTest1(){
        // tests from blackbox testing module
        assertThrows(IllegalArgumentException.class, () -> userRepository.getAllByLastLoginBetween(Date.valueOf("banane"), Date.valueOf("")));
        // test from whitebox testing module
        assertThrows(IndexOutOfBoundsException.class, () -> outfitService.getFirstColor(emptyList()));
        Color color = new Color();
        color.setName("gray");
        assertEquals(color, outfitService.getFirstColor(singletonList(color)));

    }

    @Test
    public void integrationTest2(){
        // tests from blackbox testing module
        assertThrows(IllegalArgumentException.class, () -> userRepository.getAllByLastLoginBetween(Date.valueOf("1.1.1"), null));
        // test from whitebox testing module
        assertThrows(IndexOutOfBoundsException.class, () -> outfitService.getFirstColor(emptyList()));
        Color color = new Color();
        color.setName("gray");
        color.setName("blue");
        assertEquals(color, outfitService.getFirstColor(singletonList(color)));
    }

    @Test
    public void integrationTest3(){
        // tests from blackbox testing module
        assertFalse(userRepository.getAllByLastLoginBetween(Date.valueOf("2020-01-01"), Date.valueOf("2020-05-01")).isEmpty());
        // test from whitebox testing module
        Color color = new Color();
        color.setName("gray");
        color.setName("blue");
        Color black = new Color();
        black.setName("black");
        assertEquals(color, outfitService.getFirstColor(asList(black, color)));

    }

    @After
    public void deleteTestData() {
        userRepository.deleteAll();
        clothingRepository.deleteAll();
        typeRepository.deleteAll();
        categoryRepository.deleteAll();
        colorRepository.deleteAll();
    }
}
