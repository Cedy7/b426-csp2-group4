package com.joysistvi.ribbons.view;

import com.joysistvi.ribbons.model.Category;
import com.joysistvi.ribbons.model.Item;
import com.joysistvi.ribbons.model.Size;
import com.joysistvi.ribbons.model.enums.Gender;
import com.joysistvi.ribbons.model.enums.ItemStatus;
import com.joysistvi.ribbons.service.CategoryService;
import com.joysistvi.ribbons.service.ItemService;
import com.joysistvi.ribbons.service.SizeService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ItemView {

    private final ItemService itemService;
    private final CategoryService categoryService;
    private final SizeService sizeService;

    public ItemView(ItemService itemService, CategoryService categoryService, SizeService sizeService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.sizeService = sizeService;
    }

    public void handleMenu() {
        while (true) {
            ConsoleInput.clearScreen();
            System.out.println("\n=== MANAGE ITEMS ===");
            System.out.println("1. View All Active Items");
            System.out.println("2. Add Item(s)");
            System.out.println("3. Update Item");
            System.out.println("4. Update Item Status");
            System.out.println("5. Search Items");
            System.out.println("6. View Items by Status");
            System.out.println("7. Archive Item");
            System.out.println("8. Restore Archived Item");
            System.out.println("0. Back");

            int choice = ConsoleInput.readInt("Choose an option: ");
            switch (choice) {
                case 1 -> displayAllItems();
                case 2 -> promptCreateItems();
                case 3 -> promptUpdateItem();
                case 4 -> promptUpdateItemStatus();
                case 5 -> promptSearchItems();
                case 6 -> promptItemsByStatus();
                case 7 -> promptArchiveItem();
                case 8 -> promptRestoreItem();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }

            ConsoleInput.pause();
        }
    }

    public void displayAllItems() {
        List<Item> items = itemService.getAllActiveItems();

        System.out.println("\n----- Items -----");
        printItems(items);
    }

    public void promptCreateItems() {
        System.out.println("\n----- Add Item(s) -----");

        List<Category> categories = categoryService.getAllActiveCategories();

        if (categories.isEmpty()) {
            System.out.println("No categories found. Please add a category first.");
            return;
        }

        List<Size> sizes = sizeService.getAllSizes();

        if (sizes.isEmpty()) {
            System.out.println("No sizes found. Please add a size first.");
            return;
        }

        printCategories(categories);
        int categoryId = ConsoleInput.readInt("Enter category ID: ");

        printSizes(sizes);
        int sizeId = ConsoleInput.readInt("Enter size ID: ");

        String name = ConsoleInput.readString("Enter item name: ");
        String color = ConsoleInput.readString("Enter color: ");
        Gender gender = promptGender();

        BigDecimal rentalPrice = readPrice("Enter rental price: ");

        if (rentalPrice == null) {
            return;
        }

        BigDecimal replacementCost = readPrice("Enter replacement cost: ");

        if (replacementCost == null) {
            return;
        }

        System.out.println("Note: Each piece gets its own SKU. Enter how many identical pieces to add.");
        int quantity = ConsoleInput.readInt("Enter quantity: ");

        List<String> createdSkus = itemService.createItems(categoryId, sizeId, name, color, gender,
                rentalPrice, replacementCost, quantity);

        if (createdSkus.isEmpty()) {
            System.out.println("Failed to create item(s).");
            return;
        }

        System.out.println("\n" + createdSkus.size() + " item(s) created successfully!");
        System.out.println("SKU(s): " + String.join(", ", createdSkus));

        if (createdSkus.size() < quantity) {
            System.out.println("Only " + createdSkus.size() + " of " + quantity + " item(s) were created.");
        }
    }

    public void promptUpdateItem() {
        displayAllItems();
        System.out.println("\n----- Update Item -----");
        int id = ConsoleInput.readInt("Enter item ID to update: ");

        Item item = itemService.getItemById(id);

        if (item == null) {
            System.out.println("Item not found.");
            return;
        }

        System.out.println("SKU: " + item.getSku() + " | Category: " + item.getCategoryName() + " (cannot be changed)");
        System.out.println("Note: Press Enter without typing anything to keep the current value.");

        String name = ConsoleInput.readString("Enter new name [" + item.getName() + "]: ");
        String color = ConsoleInput.readString("Enter new color [" + item.getColor() + "]: ");
        String rentalPriceInput = ConsoleInput.readString("Enter new rental price [" + item.getRentalPrice() + "]: ");
        String replacementCostInput = ConsoleInput.readString("Enter new replacement cost [" + item.getReplacementCost() + "]: ");

        if (name.trim().isEmpty()) {
            name = item.getName();
        }

        if (color.trim().isEmpty()) {
            color = item.getColor();
        }

        BigDecimal rentalPrice = item.getRentalPrice();

        if (!rentalPriceInput.trim().isEmpty()) {
            rentalPrice = parsePrice(rentalPriceInput);

            if (rentalPrice == null) {
                return;
            }
        }

        BigDecimal replacementCost = item.getReplacementCost();

        if (!replacementCostInput.trim().isEmpty()) {
            replacementCost = parsePrice(replacementCostInput);

            if (replacementCost == null) {
                return;
            }
        }

        int sizeId = item.getSizeId();
        System.out.println("Current size: " + item.getSizeCode());
        String changeSize = ConsoleInput.readString("Change size? (Y/N) [N]: ");

        if (changeSize.trim().equalsIgnoreCase("Y")) {
            printSizes(sizeService.getAllSizes());
            sizeId = ConsoleInput.readInt("Enter size ID: ");
        }

        Gender gender = item.getGender();
        System.out.println("Current gender: " + item.getGender());
        String changeGender = ConsoleInput.readString("Change gender? (Y/N) [N]: ");

        if (changeGender.trim().equalsIgnoreCase("Y")) {
            gender = promptGender();
        }

        boolean updated = itemService.updateItem(id, sizeId, name, color, gender, rentalPrice, replacementCost);

        if (updated) {
            System.out.println("\nItem " + item.getSku() + " updated successfully!");
        } else {
            System.out.println("Failed to update item.");
        }
    }

    public void promptUpdateItemStatus() {
        displayAllItems();
        System.out.println("\n----- Update Item Status -----");
        int id = ConsoleInput.readInt("Enter item ID: ");

        Item item = itemService.getItemById(id);

        if (item == null) {
            System.out.println("Item not found.");
            return;
        }

        System.out.println("Current status of " + item.getSku() + ": " + item.getStatus());
        System.out.println("New status:");
        System.out.println("1. AVAILABLE");
        System.out.println("2. FOR_LAUNDRY");
        System.out.println("3. FOR_REPAIR");
        System.out.println("4. LOST");
        int choice = ConsoleInput.readInt("Choose status: ");

        ItemStatus status = switch (choice) {
            case 1 -> ItemStatus.AVAILABLE;
            case 2 -> ItemStatus.FOR_LAUNDRY;
            case 3 -> ItemStatus.FOR_REPAIR;
            case 4 -> ItemStatus.LOST;
            default -> null;
        };

        if (status == null) {
            System.out.println("Invalid status.");
            return;
        }

        boolean updated = itemService.updateItemStatus(id, status);

        if (updated) {
            System.out.println("\nItem " + item.getSku() + " is now " + status + ".");
        } else {
            System.out.println("Failed to update item status.");
        }
    }

    public void promptSearchItems() {
        System.out.println("\n----- Search Items -----");
        String keyword = ConsoleInput.readString("Enter SKU, name, color, or category: ");

        if (keyword == null || keyword.trim().isEmpty()) {
            itemService.searchItems(keyword);
            return;
        }

        List<Item> items = itemService.searchItems(keyword);

        System.out.println("\n----- Search Items (Keyword: '" + keyword.trim() + "') -----");
        printItems(items);
    }

    public void promptItemsByStatus() {
        System.out.println("\n----- View Items by Status -----");
        ItemStatus[] statuses = ItemStatus.values();

        for (int i = 0; i < statuses.length; i++) {
            System.out.println((i + 1) + ". " + statuses[i]);
        }

        int choice = ConsoleInput.readInt("Choose status: ");

        if (choice < 1 || choice > statuses.length) {
            System.out.println("Invalid status.");
            return;
        }

        ItemStatus status = statuses[choice - 1];
        List<Item> items = itemService.getItemsByStatus(status);

        System.out.println("\n----- Items (" + status + ") -----");
        printItems(items);
    }

    public void promptArchiveItem() {
        displayAllItems();
        System.out.println("\n----- Archive Item -----");
        int id = ConsoleInput.readInt("Enter item ID to archive: ");

        boolean archived = itemService.archiveItem(id);

        if (archived) {
            System.out.println("\nItem ID " + id + " archived successfully!");
        } else {
            System.out.println("Failed to archive item.");
        }
    }

    public void promptRestoreItem() {
        System.out.println("\n----- Restore Item -----");

        List<Item> archivedItems = itemService.getAllArchivedItems();

        if (archivedItems.isEmpty()) {
            System.out.println("No archived items found.");
            return;
        }

        System.out.println("\n----- Archived Items -----");
        printItems(archivedItems);

        int id = ConsoleInput.readInt("Enter item ID to restore: ");

        boolean restored = itemService.restoreItem(id);

        if (restored) {
            System.out.println("\nItem ID " + id + " restored successfully!");
        } else {
            System.out.println("Failed to restore item.");
        }
    }

    private Gender promptGender() {
        while (true) {
            System.out.println("Gender:");
            System.out.println("1. MALE");
            System.out.println("2. FEMALE");
            System.out.println("3. UNISEX");
            int choice = ConsoleInput.readInt("Choose gender: ");

            switch (choice) {
                case 1 -> {
                    return Gender.MALE;
                }
                case 2 -> {
                    return Gender.FEMALE;
                }
                case 3 -> {
                    return Gender.UNISEX;
                }
                default -> System.out.println("Invalid gender. Please try again.");
            }
        }
    }

    private BigDecimal readPrice(String prompt) {
        return parsePrice(ConsoleInput.readString(prompt));
    }

    private BigDecimal parsePrice(String input) {
        try {
            return new BigDecimal(input.trim()).setScale(2, RoundingMode.HALF_UP);
        } catch (NumberFormatException e) {
            System.out.println("\nValidation Error: Price must be a valid number (e.g. 1500 or 1500.50).");
            return null;
        }
    }

    private void printCategories(List<Category> categories) {
        String border = ConsoleInput.border(5, 5, 25);

        System.out.println(border);
        System.out.printf("| %-5s | %-5s | %-25s |%n", "ID", "Code", "Category");
        System.out.println(border);

        for (Category category : categories) {
            System.out.printf("| %-5d | %-5s | %-25s |%n",
                    category.getId(),
                    category.getCode(),
                    ConsoleInput.truncate(category.getName(), 25));
        }

        System.out.println(border);
    }

    private void printSizes(List<Size> sizes) {
        String border = ConsoleInput.border(5, 5);

        System.out.println(border);
        System.out.printf("| %-5s | %-5s |%n", "ID", "Size");
        System.out.println(border);

        for (Size size : sizes) {
            System.out.printf("| %-5d | %-5s |%n", size.getId(), size.getCode());
        }

        System.out.println(border);
    }

    private void printItems(List<Item> items) {
        if (items == null || items.isEmpty()) {
            System.out.println("No items found.");
            return;
        }

        String border = ConsoleInput.border(5, 10, 25, 18, 5, 12, 7, 10, 11);

        System.out.println(border);
        System.out.printf("| %-5s | %-10s | %-25s | %-18s | %-5s | %-12s | %-7s | %10s | %-11s |%n",
                "ID", "SKU", "Name", "Category", "Size", "Color", "Gender", "Price", "Status");
        System.out.println(border);

        for (Item item : items) {
            System.out.printf("| %-5d | %-10s | %-25s | %-18s | %-5s | %-12s | %-7s | %10s | %-11s |%n",
                    item.getId(),
                    item.getSku(),
                    ConsoleInput.truncate(item.getName(), 25),
                    ConsoleInput.truncate(item.getCategoryName(), 18),
                    item.getSizeCode(),
                    ConsoleInput.truncate(item.getColor(), 12),
                    item.getGender(),
                    item.getRentalPrice(),
                    item.getStatus());
        }

        System.out.println(border);
    }
}