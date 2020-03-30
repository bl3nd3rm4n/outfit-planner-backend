package com.lid.outfitplannerbackend.services;

import java.util.List;

public interface IService<T> {

    List<T> getAll();

    T getById(int id);

    //T insert(T entity);
}
