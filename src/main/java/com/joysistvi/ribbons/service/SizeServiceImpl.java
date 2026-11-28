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
    public List<Size> getAllSizes() {
        return sizeRepository.findAll();
    }

    @Override
    public Size getSizeById(int id) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return null;
        }

        return sizeRepository.findById(id);
    }

    @Override
    public boolean createSize(String code, int sortOrder) {
        if (!isValidCode(code)) {
            System.out.println("\nValidation Error: Code must be 1 to 5 letters or numbers only.");
            return false;
        }

        if (sortOrder <= 0) {
            System.out.println("\nValidation Error: Sort order must be greater than 0.");
            return false;
        }

        String cleanCode = code.trim().toUpperCase();

        if (sizeRepository.findByCode(cleanCode) != null) {
            System.out.println("\nValidation Error: Size code already exists.");
            return false;
        }

        Size size = new Size(cleanCode, sortOrder);
        return sizeRepository.save(size);
    }

    @Override
    public boolean updateSize(int id, String code, int sortOrder) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return false;
        }

        if (!isValidCode(code)) {
            System.out.println("\nValidation Error: Code must be 1 to 5 letters or numbers only.");
            return false;
        }

        if (sortOrder <= 0) {
            System.out.println("\nValidation Error: Sort order must be greater than 0.");
            return false;
        }

        Size existing = sizeRepository.findById(id);

        if (existing == null) {
            System.out.println("\nValidation Error: Size not found.");
            return false;
        }

        String cleanCode = code.trim().toUpperCase();

        Size sameCode = sizeRepository.findByCode(cleanCode);
        if (sameCode != null && sameCode.getId() != id) {
            System.out.println("\nValidation Error: Size code already exists.");
            return false;
        }

        existing.setCode(cleanCode);
        existing.setSortOrder(sortOrder);
        return sizeRepository.update(existing);
    }

    @Override
    public boolean deleteSize(int id) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return false;
        }

        return sizeRepository.deleteById(id);
    }

    private boolean isValidCode(String code) {
        return code != null && code.trim().matches("[A-Za-z0-9]{1,5}");
    }
}