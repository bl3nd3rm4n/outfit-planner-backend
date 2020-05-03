package com.lid.outfitplannerbackend;

import com.lid.outfitplannerbackend.model.User;
import com.lid.outfitplannerbackend.persistence.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan("main.java.persistence.*")
public class OutfitPlannerBackendApplicationTests {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void contextLoads() {
    }

    @Before
    public void addTestData() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setLastLogin(Date.valueOf("2020-03-03"));
        userRepository.saveAndFlush(user);
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

    @After
    public void deleteTestData() {
        userRepository.deleteAll();
    }
}
