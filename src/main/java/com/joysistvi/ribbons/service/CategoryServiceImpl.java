package com.joysistvi.ribbons.service;

import com.joysistvi.ribbons.model.Category;
import com.joysistvi.ribbons.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllActiveCategories() {
        return categoryRepository.findAllActive();
    }

    @Override
    public List<Category> getAllArchivedCategories() {
        return categoryRepository.findAllArchived();
    }

    @Override
    public Category getCategoryById(int id) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return null;
        }

        return categoryRepository.findById(id);
    }

    @Override
    public boolean createCategory(String code, String name, String description) {
        if (!isValidCode(code)) {
            System.out.println("\nValidation Error: Code must be 2 to 5 letters only.");
            return false;
        }

        if (!isValidName(name)) {
            return false;
        }

        if (!isValidDescription(description)) {
            return false;
        }

        String cleanCode = code.trim().toUpperCase();

        if (categoryRepository.findByCode(cleanCode) != null) {
            System.out.println("\nValidation Error: Category code already exists.");
            return false;
        }

        if (categoryRepository.findByName(name.trim()) != null) {
            System.out.println("\nValidation Error: Category name already exists.");
            return false;
        }

        Category category = new Category(cleanCode, name.trim(), toNullIfBlank(description), false);
        return categoryRepository.save(category);
    }

    @Override
    public boolean updateCategory(int id, String name, String description) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return false;
        }

        if (!isValidName(name)) {
            return false;
        }

        if (!isValidDescription(description)) {
            return false;
        }

        Category existing = categoryRepository.findById(id);

        if (existing == null) {
            System.out.println("\nValidation Error: Category not found.");
            return false;
        }

        Category sameName = categoryRepository.findByName(name.trim());
        if (sameName != null && sameName.getId() != id) {
            System.out.println("\nValidation Error: Category name already exists.");
            return false;
        }

        existing.setName(name.trim());
        existing.setDescription(toNullIfBlank(description));
        return categoryRepository.update(existing);
    }

    @Override
    public boolean archiveCategory(int id) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return false;
        }

        return categoryRepository.archiveById(id);
    }

    @Override
    public boolean restoreCategory(int id) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return false;
        }

        return categoryRepository.restoreById(id);
    }

    @Override
    public List<Category> searchCategories(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("\nValidation Error: Search keyword is required.");
            return new ArrayList<>();
        }

        return categoryRepository.searchByNameOrCode(keyword.trim());
    }

    private boolean isValidCode(String code) {
        return code != null && code.trim().matches("[A-Za-z]{2,5}");
    }

    private boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("\nValidation Error: Category name is required.");
            return false;
        }

        if (name.trim().length() > 50) {
            System.out.println("\nValidation Error: Category name must be 50 characters or less.");
            return false;
        }

        return true;
    }

    private boolean isValidDescription(String description) {
        if (description != null && description.trim().length() > 255) {
            System.out.println("\nValidation Error: Description must be 255 characters or less.");
            return false;
        }

        return true;
    }

    private String toNullIfBlank(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }
}