package com.joysistvi.ribbons.repository;

import com.joysistvi.ribbons.model.Size;

import java.util.List;

public interface SizeRepository {

    Size findById(int id);

    List<Size> findAllAvailableSize();

    List<Size> findBySortorder(int sortOrder);


}
