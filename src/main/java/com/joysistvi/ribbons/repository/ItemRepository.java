package com.joysistvi.ribbons.repository;

import com.joysistvi.ribbons.model.Item;
import com.joysistvi.ribbons.model.enums.ItemStatus;

import java.util.List;

public interface ItemRepository {

    List<Item> findAllActive();

    List<Item> findAllArchived();

    List<Item> findByStatus(ItemStatus status);

    Item findById(int id);

    Item findBySku(String sku);

    String findLastSkuByPrefix(String prefix);

    boolean save(Item item);

    boolean update(Item item);

    boolean updateStatus(int id, ItemStatus status);

    boolean archiveById(int id);

    boolean restoreById(int id);

    List<Item> searchByKeyword(String keyword);
}