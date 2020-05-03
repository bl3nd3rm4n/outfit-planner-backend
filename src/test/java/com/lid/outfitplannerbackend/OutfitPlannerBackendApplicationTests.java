package com.lid.outfitplannerbackend;

import com.lid.outfitplannerbackend.model.*;
import com.lid.outfitplannerbackend.persistence.*;
import com.lid.outfitplannerbackend.services.ClothingService;
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

    private void addClothes(Integer userId) throws IOException {
        String prefix = "data:image/png;base64,";

        Clothing tShirt1 = new Clothing();
        tShirt1.setType(typeRepository.findAll().get(0));
        tShirt1.setCategories(singletonList(categoryRepository.findAll().get(0)));
        File picture1 = new File("src\\test\\resources\\tops\\hmgoepprod1.jpg");
        tShirt1.setPicture(concatenate(prefix.getBytes(), Base64.getEncoder().encode(Files.readAllBytes(picture1.toPath()))));
        Clothing insert = clothingService.insert(tShirt1);
        userService.insertClothing(userId, insert);

        Clothing tShirt2 = new Clothing();
        tShirt2.setType(typeRepository.findAll().get(0));
        tShirt2.setCategories(singletonList(categoryRepository.findAll().get(0)));
        File picture2 = new File("src\\test\\resources\\tops\\hmgoepprod2.jpg");
        tShirt2.setPicture(concatenate(prefix.getBytes(), Base64.getEncoder().encode(Files.readAllBytes(picture2.toPath()))));
        Clothing insert1 = clothingService.insert(tShirt2);
        userService.insertClothing(userId, insert1);

        Clothing tShirt3 = new Clothing();
        tShirt3.setType(typeRepository.findAll().get(0));
        tShirt3.setCategories(singletonList(categoryRepository.findAll().get(0)));
        File picture3 = new File("src\\test\\resources\\tops\\hmgoepprod3.jpg");
        tShirt3.setPicture(concatenate(prefix.getBytes(), Base64.getEncoder().encode(Files.readAllBytes(picture3.toPath()))));
        Clothing insert2 = clothingService.insert(tShirt3);
        userService.insertClothing(userId, insert2);

        Clothing pants1 = new Clothing();
        pants1.setType(typeRepository.findAll().get(1));
        pants1.setCategories(categoryRepository.findAll());
        File picturePants1 = new File("src\\test\\resources\\pants\\hmgoepprod1.jpg");
        pants1.setPicture(concatenate(prefix.getBytes(), Base64.getEncoder().encode(Files.readAllBytes(picturePants1.toPath()))));
        Clothing insert3 = clothingService.insert(pants1);
        userService.insertClothing(userId, insert3);

        Clothing pants2 = new Clothing();
        pants2.setType(typeRepository.findAll().get(1));
        pants2.setCategories(categoryRepository.findAll());
        File picturePants2 = new File("src\\test\\resources\\pants\\hmgoepprod2.jpg");
        pants2.setPicture(concatenate(prefix.getBytes(), Base64.getEncoder().encode(Files.readAllBytes(picturePants2.toPath()))));
        Clothing insert4 = clothingService.insert(pants2);
        userService.insertClothing(userId, insert4);

        Clothing pants3 = new Clothing();
        pants3.setType(typeRepository.findAll().get(1));
        pants3.setCategories(categoryRepository.findAll());
        File picturePants3 = new File("src\\test\\resources\\pants\\hmgoepprod3.jpg");
        pants3.setPicture(concatenate(prefix.getBytes(), Base64.getEncoder().encode(Files.readAllBytes(picturePants3.toPath()))));
        Clothing insert5 = clothingService.insert(pants3);
        userService.insertClothing(userId, insert5);
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

    @After
    public void deleteTestData() {
        userRepository.deleteAll();
        clothingRepository.deleteAll();
        typeRepository.deleteAll();
        categoryRepository.deleteAll();
        colorRepository.deleteAll();
    }
}
