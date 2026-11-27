package com.joysistvi.ribbons.service;


import com.joysistvi.ribbons.model.Size;
import com.joysistvi.ribbons.repository.SizeRepository;

import java.util.List;

public class SizeServiceImpl implements SizeService {

    private final SizeRepository sizeRepository;

    public SizeServiceImpl(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    @Override
    public Size getSizeById(int id) {
        if (id <= 0 ) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return null;
        }

        return sizeRepository.findById(id);
    }

    @Override
    public List<Size> getAllAvailableSize() {
        return sizeRepository.findAllAvailableSize();
    }

    @Override
    public List<Size> getBySortorder(int sortOrder) {
        if (sortOrder <= 0 ) {
            System.out.println("\nValidation Error: Sort Order must be greater than 0.");
            return null;
        }

        return sizeRepository.findBySortorder(sortOrder);
    }
}
