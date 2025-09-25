package com.library.dao.generics.interfaces;

import java.util.List;
import java.util.Optional;

public interface IGenericDAO<T, ID> {
    T createGeneric(T entity);
    T updateGeneric(T entity);
    List<T> searchAllGenerics();
    Optional<T> searchGenericById(ID id);
    void deleteGenericById(ID id);
}