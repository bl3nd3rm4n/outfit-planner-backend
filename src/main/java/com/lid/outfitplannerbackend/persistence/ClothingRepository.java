package com.lid.outfitplannerbackend.persistence;

import com.lid.outfitplannerbackend.model.Clothing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothingRepository extends JpaRepository<Clothing, Integer> {
}
