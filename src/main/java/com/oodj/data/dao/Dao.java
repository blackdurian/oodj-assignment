package com.oodj.data.dao;

import java.io.Serializable;

public interface Dao<T, ID extends Serializable> {
    void add(T entity);
    void delete(ID id);
    void deleteAll();
    T findOne(ID id);
    boolean exists(ID id);
    Iterable<T> findAll();
    long count();
    void update(T entity);
}
