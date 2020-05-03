package com.lid.outfitplannerbackend.persistence;

import com.lid.outfitplannerbackend.model.Category;
import com.lid.outfitplannerbackend.model.Clothing;
import com.lid.outfitplannerbackend.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ClothingRepository extends JpaRepository<Clothing, Integer> {
    @Transactional
    List<Clothing> findAllByCategories(List<Category> categories);

    @Transactional
    List<Clothing> findAllByCategoriesContains(Category category);

    @Transactional
    List<Clothing> findAllByType(Type type);
}
