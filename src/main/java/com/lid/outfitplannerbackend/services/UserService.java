package com.lid.outfitplannerbackend.services;

import com.lid.outfitplannerbackend.model.Clothing;
import com.lid.outfitplannerbackend.model.Outfit;
import com.lid.outfitplannerbackend.model.User;
import com.lid.outfitplannerbackend.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class UserService implements IService<User> {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public User getById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public Clothing insertClothing(int userId, Clothing clothing) {
        User user = userRepository.getOne(userId);
        user.getClothes().add(clothing);
        userRepository.save(user);
        return clothing;
    }

    @Transactional
    public User login(String username, String password) {
        return userRepository.getByUsernameAndPassword(username, password);
    }

    @Transactional
    public User register(String username, String password) {
        if (userRepository.getByUsername(username) != null) {
            return null;
        } else {
            userRepository.save(new User(username, password));
            return userRepository.getByUsernameAndPassword(username, password);
        }
    }

    public Outfit insertOutfit(int userId, Outfit outfit) {
        User user = userRepository.getOne(userId);
        user.getOutfits().add(outfit);
        userRepository.save(user);
        return outfit;
    }
}
