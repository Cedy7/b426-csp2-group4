package com.joysistvi.ribbons.service;

import com.joysistvi.ribbons.model.Item;
import com.joysistvi.ribbons.model.enums.Gender;
import com.joysistvi.ribbons.model.enums.ItemStatus;

import java.math.BigDecimal;
import java.util.List;

public interface ItemService {

    List<Item> getAllActiveItems();

    List<Item> getAllArchivedItems();

    List<Item> getItemsByStatus(ItemStatus status);

    Item getItemById(int id);

    List<String> createItems(int categoryId, int sizeId, String name, String color, Gender gender,
                             BigDecimal rentalPrice, BigDecimal replacementCost, int quantity);

    boolean updateItem(int id, int sizeId, String name, String color, Gender gender,
                       BigDecimal rentalPrice, BigDecimal replacementCost);

    boolean updateItemStatus(int id, ItemStatus status);

    boolean archiveItem(int id);

    boolean restoreItem(int id);

    List<Item> searchItems(String keyword);
}