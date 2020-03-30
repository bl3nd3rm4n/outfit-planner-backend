package com.lid.outfitplannerbackend.persistence;

import com.lid.outfitplannerbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User getByUsernameAndPassword(String username, String password);

    User getByUsername(String username);

    List<User> getAllByLastLogin(Date lastLogin);
}