package com.lid.outfitplannerbackend.services;

import com.lid.outfitplannerbackend.model.Type;
import com.lid.outfitplannerbackend.persistence.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class TypeService implements IService<Type> {

    private final TypeRepository typeRepository;

    @Autowired
    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Transactional
    @Override
    public List<Type> getAll() {
        return typeRepository.findAll();
    }

    @Transactional
    @Override
    public Type getById(int id) {
        return typeRepository.getOne(id);
    }
}
