package com.joysistvi.ribbons.service;

import com.joysistvi.ribbons.model.Category;
import com.joysistvi.ribbons.model.Item;
import com.joysistvi.ribbons.model.Size;
import com.joysistvi.ribbons.model.enums.Gender;
import com.joysistvi.ribbons.model.enums.ItemStatus;
import com.joysistvi.ribbons.repository.CategoryRepository;
import com.joysistvi.ribbons.repository.ItemRepository;
import com.joysistvi.ribbons.repository.SizeRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemServiceImpl implements ItemService {

    private static final int MAX_QUANTITY = 50;

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final SizeRepository sizeRepository;

    public ItemServiceImpl(ItemRepository itemRepository, CategoryRepository categoryRepository, SizeRepository sizeRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.sizeRepository = sizeRepository;
    }

    @Override
    public List<Item> getAllActiveItems() {
        return itemRepository.findAllActive();
    }

    @Override
    public List<Item> getAllArchivedItems() {
        return itemRepository.findAllArchived();
    }

    @Override
    public List<Item> getItemsByStatus(ItemStatus status) {
        if (status == null) {
            System.out.println("\nValidation Error: Status is required.");
            return new ArrayList<>();
        }

        return itemRepository.findByStatus(status);
    }

    @Override
    public Item getItemById(int id) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return null;
        }

        return itemRepository.findById(id);
    }

    @Override
    public List<String> createItems(int categoryId, int sizeId, String name, String color, Gender gender,
                                    BigDecimal rentalPrice, BigDecimal replacementCost, int quantity) {
        List<String> createdSkus = new ArrayList<>();

        Category category = categoryRepository.findById(categoryId);

        if (category == null || category.isArchived()) {
            System.out.println("\nValidation Error: Category not found.");
            return createdSkus;
        }

        if (!isValidItem(sizeId, name, color, gender, rentalPrice, replacementCost)) {
            return createdSkus;
        }

        if (quantity <= 0 || quantity > MAX_QUANTITY) {
            System.out.println("\nValidation Error: Quantity must be between 1 and " + MAX_QUANTITY + ".");
            return createdSkus;
        }

        String prefix = category.getCode() + "-";
        int nextNumber = getNextSkuNumber(prefix);

        for (int i = 0; i < quantity; i++) {
            String sku = prefix + String.format("%04d", nextNumber + i);

            Item item = new Item(categoryId, sizeId, name.trim(), color.trim(), gender, rentalPrice, replacementCost);
            item.setSku(sku);

            if (!itemRepository.save(item)) {
                break;
            }

            createdSkus.add(sku);
        }

        return createdSkus;
    }

    @Override
    public boolean updateItem(int id, int sizeId, String name, String color, Gender gender,
                              BigDecimal rentalPrice, BigDecimal replacementCost) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return false;
        }

        if (!isValidItem(sizeId, name, color, gender, rentalPrice, replacementCost)) {
            return false;
        }

        Item existing = itemRepository.findById(id);

        if (existing == null) {
            System.out.println("\nValidation Error: Item not found.");
            return false;
        }

        existing.setSizeId(sizeId);
        existing.setName(name.trim());
        existing.setColor(color.trim());
        existing.setGender(gender);
        existing.setRentalPrice(rentalPrice);
        existing.setReplacementCost(replacementCost);
        return itemRepository.update(existing);
    }

    @Override
    public boolean updateItemStatus(int id, ItemStatus status) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return false;
        }

        if (status == null) {
            System.out.println("\nValidation Error: Status is required.");
            return false;
        }

        if (status == ItemStatus.RESERVED || status == ItemStatus.RENTED) {
            System.out.println("\nValidation Error: RESERVED and RENTED are set by rentals only.");
            return false;
        }

        Item existing = itemRepository.findById(id);

        if (existing == null) {
            System.out.println("\nValidation Error: Item not found.");
            return false;
        }

        if (isInRental(existing)) {
            System.out.println("\nValidation Error: Item is currently " + existing.getStatus() + " and cannot be changed here.");
            return false;
        }

        if (existing.getStatus() == status) {
            System.out.println("\nValidation Error: Item is already " + status + ".");
            return false;
        }

        return itemRepository.updateStatus(id, status);
    }

    @Override
    public boolean archiveItem(int id) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return false;
        }

        Item existing = itemRepository.findById(id);

        if (existing == null) {
            System.out.println("\nValidation Error: Item not found.");
            return false;
        }

        if (isInRental(existing)) {
            System.out.println("\nValidation Error: Item is currently " + existing.getStatus() + " and cannot be archived.");
            return false;
        }

        return itemRepository.archiveById(id);
    }

    @Override
    public boolean restoreItem(int id) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return false;
        }

        return itemRepository.restoreById(id);
    }

    @Override
    public List<Item> searchItems(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("\nValidation Error: Search keyword is required.");
            return new ArrayList<>();
        }

        return itemRepository.searchByKeyword(keyword.trim());
    }

    private boolean isValidItem(int sizeId, String name, String color, Gender gender,
                                BigDecimal rentalPrice, BigDecimal replacementCost) {
        Size size = sizeRepository.findById(sizeId);

        if (size == null) {
            System.out.println("\nValidation Error: Size not found.");
            return false;
        }

        if (name == null || name.trim().isEmpty()) {
            System.out.println("\nValidation Error: Item name is required.");
            return false;
        }

        if (name.trim().length() > 100) {
            System.out.println("\nValidation Error: Item name must be 100 characters or less.");
            return false;
        }

        if (color == null || color.trim().isEmpty()) {
            System.out.println("\nValidation Error: Color is required.");
            return false;
        }

        if (color.trim().length() > 30) {
            System.out.println("\nValidation Error: Color must be 30 characters or less.");
            return false;
        }

        if (gender == null) {
            System.out.println("\nValidation Error: Gender is required.");
            return false;
        }

        if (rentalPrice == null || rentalPrice.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("\nValidation Error: Rental price must be greater than 0.");
            return false;
        }

        if (replacementCost == null || replacementCost.compareTo(rentalPrice) < 0) {
            System.out.println("\nValidation Error: Replacement cost must not be lower than the rental price.");
            return false;
        }

        return true;
    }

    private boolean isInRental(Item item) {
        return item.getStatus() == ItemStatus.RESERVED || item.getStatus() == ItemStatus.RENTED;
    }

    private int getNextSkuNumber(String prefix) {
        String lastSku = itemRepository.findLastSkuByPrefix(prefix);

        if (lastSku == null) {
            return 1;
        }

        try {
            return Integer.parseInt(lastSku.substring(prefix.length())) + 1;
        } catch (NumberFormatException e) {
            return 1;
        }
    }
}