package com.joysistvi.ribbons.repository;

import com.joysistvi.ribbons.model.Size;

import java.util.List;

public interface SizeRepository {

    List<Size> findAll();

    Size findById(int id);

    Size findByCode(String code);

    boolean save(Size size);

    boolean update(Size size);

    boolean deleteById(int id);
}