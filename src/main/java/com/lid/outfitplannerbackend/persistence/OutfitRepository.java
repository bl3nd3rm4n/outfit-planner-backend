package com.lid.outfitplannerbackend.persistence;

import com.lid.outfitplannerbackend.model.Outfit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutfitRepository extends JpaRepository<Outfit, Integer> {
}
