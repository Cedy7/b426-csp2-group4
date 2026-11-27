package com.joysistvi.ribbons.view;

import com.joysistvi.ribbons.model.Category;
import com.joysistvi.ribbons.service.CategoryService;

import java.util.List;

public class CategoryView {

    private final CategoryService categoryService;

    public CategoryView(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void handleMenu() {
        while (true) {
            ConsoleInput.clearScreen();
            System.out.println("\n=== MANAGE CATEGORIES ===");
            System.out.println("1. View All Active Categories");
            System.out.println("2. Add Category");
            System.out.println("3. Update Category");
            System.out.println("4. Search Categories");
            System.out.println("5. Archive Category");
            System.out.println("6. Restore Archived Category");
            System.out.println("0. Back");

            int choice = ConsoleInput.readInt("Choose an option: ");
            switch (choice) {
                case 1 -> displayAllCategories();
                case 2 -> promptCreateCategory();
                case 3 -> promptUpdateCategory();
                case 4 -> promptSearchCategories();
                case 5 -> promptArchiveCategory();
                case 6 -> promptRestoreCategory();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }

            ConsoleInput.pause();
        }
    }

    public void displayAllCategories() {
        List<Category> categories = categoryService.getAllActiveCategories();

        System.out.println("\n----- Categories -----");
        printCategories(categories);
    }

    public void promptCreateCategory() {
        System.out.println("\n----- Add Category -----");
        System.out.println("Note: Code is used for item SKUs (e.g. BRG for Barong). It cannot be changed later.");
        System.out.println("      Description is optional. Press Enter to skip.");
        String code = ConsoleInput.readString("Enter code (2-5 letters): ");
        String name = ConsoleInput.readString("Enter name: ");
        String description = ConsoleInput.readString("Enter description (optional): ");

        boolean created = categoryService.createCategory(code, name, description);

        if (created) {
            System.out.println("\nCategory '" + name.trim() + "' created successfully!");
        } else {
            System.out.println("Failed to create category.");
        }
    }

    public void promptUpdateCategory() {
        displayAllCategories();
        System.out.println("\n----- Update Category -----");
        int id = ConsoleInput.readInt("Enter category ID to update: ");

        Category category = categoryService.getCategoryById(id);

        if (category == null) {
            System.out.println("Category not found.");
            return;
        }

        System.out.println("Code: " + category.getCode() + " (cannot be changed)");
        System.out.println("Note: Press Enter without typing anything to keep the current value.");
        System.out.println("      Type '-' to clear description.");
        String name = ConsoleInput.readString("Enter new name [" + category.getName() + "]: ");
        String description = ConsoleInput.readString("Enter new description [" + displayValue(category.getDescription()) + "]: ");

        if (name.trim().isEmpty()) {
            name = category.getName();
        }

        if (description.trim().isEmpty()) {
            description = category.getDescription();
        } else if (description.trim().equals("-")) {
            description = null;
        }

        boolean updated = categoryService.updateCategory(id, name, description);

        if (updated) {
            System.out.println("\nCategory ID " + id + " updated successfully!");
            displayAllCategories();
        } else {
            System.out.println("Failed to update category.");
        }
    }

    public void promptSearchCategories() {
        System.out.println("\n----- Search Categories -----");
        String keyword = ConsoleInput.readString("Enter name or code: ");

        if (keyword == null || keyword.trim().isEmpty()) {
            categoryService.searchCategories(keyword);
            return;
        }

        List<Category> categories = categoryService.searchCategories(keyword);

        System.out.println("\n----- Search Categories (Keyword: '" + keyword.trim() + "') -----");
        printCategories(categories);
    }

    public void promptArchiveCategory() {
        displayAllCategories();
        System.out.println("\n----- Archive Category -----");
        int id = ConsoleInput.readInt("Enter category ID to archive: ");

        boolean archived = categoryService.archiveCategory(id);

        if (archived) {
            System.out.println("\nCategory ID " + id + " archived successfully!");
            displayAllCategories();
        } else {
            System.out.println("Failed to archive category.");
        }
    }

    public void promptRestoreCategory() {
        System.out.println("\n----- Restore Category -----");

        List<Category> archivedCategories = categoryService.getAllArchivedCategories();

        if (archivedCategories.isEmpty()) {
            System.out.println("No archived categories found.");
            return;
        }

        System.out.println("\n----- Archived Categories -----");
        printCategories(archivedCategories);

        int id = ConsoleInput.readInt("Enter category ID to restore: ");

        boolean restored = categoryService.restoreCategory(id);

        if (restored) {
            System.out.println("\nCategory ID " + id + " restored successfully!");
            displayAllCategories();
        } else {
            System.out.println("Failed to restore category.");
        }
    }

    private String displayValue(String value) {
        return value == null ? "none" : value;
    }

    private void printCategories(List<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            System.out.println("No categories found.");
            return;
        }

        String border = ConsoleInput.border(5, 5, 25, 40);

        System.out.println(border);
        System.out.printf("| %-5s | %-5s | %-25s | %-40s |%n", "ID", "Code", "Name", "Description");
        System.out.println(border);

        for (Category category : categories) {
            System.out.printf("| %-5d | %-5s | %-25s | %-40s |%n",
                    category.getId(),
                    category.getCode(),
                    ConsoleInput.truncate(category.getName(), 25),
                    ConsoleInput.truncate(category.getDescription(), 40));
        }

        System.out.println(border);
    }
}