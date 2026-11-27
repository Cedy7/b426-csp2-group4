package com.joysistvi.ribbons.service;

import com.joysistvi.ribbons.model.Size;

import java.util.List;

public interface SizeService {

    Size getSizeById(int id);

    List<Size> getAllAvailableSize();

    List<Size> getBySortorder(int sortOrder);
}
