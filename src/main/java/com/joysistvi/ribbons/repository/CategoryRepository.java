package com.joysistvi.ribbons.repository;

import com.joysistvi.ribbons.model.Category;

import java.util.List;

public interface CategoryRepository {

    List<Category> findAllActive();

    List<Category> findAllArchived();

    Category findById(int id);

    Category findByCode(String code);

    Category findByName(String name);

    boolean save(Category category);

    boolean update(Category category);

    boolean archiveById(int id);

    boolean restoreById(int id);

    List<Category> searchByNameOrCode(String keyword);
}