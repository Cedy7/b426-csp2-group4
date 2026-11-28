package com.joysistvi.ribbons.service;

import com.joysistvi.ribbons.model.Size;

import java.util.List;

public interface SizeService {

    List<Size> getAllSizes();

    Size getSizeById(int id);

    boolean createSize(String code, int sortOrder);

    boolean updateSize(int id, String code, int sortOrder);

    boolean deleteSize(int id);
}