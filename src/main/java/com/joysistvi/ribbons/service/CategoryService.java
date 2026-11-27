package com.joysistvi.ribbons.service;

import com.joysistvi.ribbons.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllActiveCategories();

    List<Category> getAllArchivedCategories();

    Category getCategoryById(int id);

    boolean createCategory(String code, String name, String description);

    boolean updateCategory(int id, String name, String description);

    boolean archiveCategory(int id);

    boolean restoreCategory(int id);

    List<Category> searchCategories(String keyword);
}