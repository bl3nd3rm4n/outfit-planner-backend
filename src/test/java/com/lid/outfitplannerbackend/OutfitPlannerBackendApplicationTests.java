package com.lid.outfitplannerbackend;

import com.lid.outfitplannerbackend.persistence.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
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

	@Test
	public void testInvalidDate() {
		assertThrows(IllegalArgumentException.class, () -> userRepository.getAllByLastLoginGreaterThan(Date.valueOf("caca maca")));
		assertThrows(IllegalArgumentException.class, () -> userRepository.getAllByLastLoginGreaterThan(Date.valueOf("1.1.1")));
	}

	@Test
	public void invalidBoundaryTest() {
		assertThrows(IllegalArgumentException.class, () -> userRepository.getAllByLastLoginGreaterThan(Date.valueOf("2019-13-13")));
		assertThrows(IllegalArgumentException.class, () -> userRepository.getAllByLastLoginGreaterThan(Date.valueOf("2020-00-00")));
	}

	@Test
	public void validBoundaryTest() {
		assertEquals(userRepository.getAllByLastLoginGreaterThan(Date.valueOf("2020-01-01")).size(), 1);
		assertTrue(userRepository.getAllByLastLoginGreaterThan(Date.valueOf("9999-12-31")).isEmpty());
	}

	@Test
    public void datePartition() {
	    assertThrows(IllegalArgumentException.class, () -> userRepository.getAllByLastLoginGreaterThan(Date.valueOf("String care nu reprezinta o data")));
	    assertThrows(IllegalArgumentException.class, () -> userRepository.getAllByLastLoginGreaterThan(Date.valueOf("333-01-01")));
	    assertThrows(IllegalArgumentException.class, () -> userRepository.getAllByLastLoginGreaterThan(Date.valueOf("55555-01-01")));
	    assertTrue(userRepository.getAllByLastLoginGreaterThan(Date.valueOf("9998-01-01")).isEmpty());
    }

}
