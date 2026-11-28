package com.joysistvi.ribbons.view;

import com.joysistvi.ribbons.model.Size;
import com.joysistvi.ribbons.service.SizeService;

import java.util.List;

public class SizeView {

    private final SizeService sizeService;

    public SizeView(SizeService sizeService) {
        this.sizeService = sizeService;
    }

    public void handleMenu() {
        while (true) {
            ConsoleInput.clearScreen();
            System.out.println("\n=== MANAGE SIZES ===");
            System.out.println("1. View All Sizes");
            System.out.println("2. Add Size");
            System.out.println("3. Update Size");
            System.out.println("4. Delete Size");
            System.out.println("0. Back");

            int choice = ConsoleInput.readInt("Choose an option: ");
            switch (choice) {
                case 1 -> displayAllSizes();
                case 2 -> promptCreateSize();
                case 3 -> promptUpdateSize();
                case 4 -> promptDeleteSize();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }

            ConsoleInput.pause();
        }
    }

    public void displayAllSizes() {
        List<Size> sizes = sizeService.getAllSizes();

        System.out.println("\n----- Sizes -----");
        printSizes(sizes);
    }

    public void promptCreateSize() {
        displayAllSizes();
        System.out.println("\n----- Add Size -----");
        System.out.println("Note: Sort order controls the display order (e.g. XS = 1, S = 2, M = 3).");
        String code = ConsoleInput.readString("Enter size code (e.g. S, M, XL, 38): ");
        int sortOrder = ConsoleInput.readInt("Enter sort order: ");

        boolean created = sizeService.createSize(code, sortOrder);

        if (created) {
            System.out.println("\nSize '" + code.trim().toUpperCase() + "' created successfully!");
            displayAllSizes();
        } else {
            System.out.println("Failed to create size.");
        }
    }

    public void promptUpdateSize() {
        displayAllSizes();
        System.out.println("\n----- Update Size -----");
        int id = ConsoleInput.readInt("Enter size ID to update: ");

        Size size = sizeService.getSizeById(id);

        if (size == null) {
            System.out.println("Size not found.");
            return;
        }

        System.out.println("Note: Press Enter without typing anything to keep the current value.");
        String code = ConsoleInput.readString("Enter new code [" + size.getCode() + "]: ");
        String sortOrderInput = ConsoleInput.readString("Enter new sort order [" + size.getSortOrder() + "]: ");

        if (code.trim().isEmpty()) {
            code = size.getCode();
        }

        int sortOrder = size.getSortOrder();

        if (!sortOrderInput.trim().isEmpty()) {
            try {
                sortOrder = Integer.parseInt(sortOrderInput.trim());
            } catch (NumberFormatException e) {
                System.out.println("\nValidation Error: Sort order must be a number.");
                return;
            }
        }

        boolean updated = sizeService.updateSize(id, code, sortOrder);

        if (updated) {
            System.out.println("\nSize ID " + id + " updated successfully!");
            displayAllSizes();
        } else {
            System.out.println("Failed to update size.");
        }
    }

    public void promptDeleteSize() {
        displayAllSizes();
        System.out.println("\n----- Delete Size -----");
        int id = ConsoleInput.readInt("Enter size ID to delete: ");

        Size size = sizeService.getSizeById(id);

        if (size == null) {
            System.out.println("Size not found.");
            return;
        }

        String confirm = ConsoleInput.readString("Are you sure you want to delete size '" + size.getCode() + "'? (Y/N): ");

        if (!confirm.trim().equalsIgnoreCase("Y")) {
            System.out.println("\nDelete cancelled.");
            return;
        }

        boolean deleted = sizeService.deleteSize(id);

        if (deleted) {
            System.out.println("\nSize '" + size.getCode() + "' deleted successfully!");
            displayAllSizes();
        } else {
            System.out.println("Failed to delete size.");
        }
    }

    private void printSizes(List<Size> sizes) {
        if (sizes == null || sizes.isEmpty()) {
            System.out.println("No sizes found.");
            return;
        }

        String border = ConsoleInput.border(5, 5, 10);

        System.out.println(border);
        System.out.printf("| %-5s | %-5s | %-10s |%n", "ID", "Code", "Sort Order");
        System.out.println(border);

        for (Size size : sizes) {
            System.out.printf("| %-5d | %-5s | %-10d |%n",
                    size.getId(),
                    size.getCode(),
                    size.getSortOrder());
        }

        System.out.println(border);
    }
}