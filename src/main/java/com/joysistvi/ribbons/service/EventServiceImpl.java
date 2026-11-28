package com.joysistvi.ribbons.service;

import com.joysistvi.ribbons.model.Event;
import com.joysistvi.ribbons.repository.EventRepository;

import java.util.ArrayList;
import java.util.List;

public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> getAllActiveEvents() {
        return eventRepository.findAllActive();
    }

    @Override
    public List<Event> getAllArchivedEvents() {
        return eventRepository.findAllArchived();
    }

    @Override
    public Event getEventById(int id) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return null;
        }

        return eventRepository.findById(id);
    }

    @Override
    public boolean createEvent(String name) {
        if (!isValidName(name)) {
            return false;
        }

        if (eventRepository.findByName(name.trim()) != null) {
            System.out.println("\nValidation Error: Event name already exists.");
            return false;
        }

        Event event = new Event(name.trim(), false);
        return eventRepository.save(event);
    }

    @Override
    public boolean updateEvent(int id, String name) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return false;
        }

        if (!isValidName(name)) {
            return false;
        }

        Event existing = eventRepository.findById(id);

        if (existing == null) {
            System.out.println("\nValidation Error: Event not found.");
            return false;
        }

        Event sameName = eventRepository.findByName(name.trim());
        if (sameName != null && sameName.getId() != id) {
            System.out.println("\nValidation Error: Event name already exists.");
            return false;
        }

        existing.setName(name.trim());
        return eventRepository.update(existing);
    }

    @Override
    public boolean archiveEvent(int id) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return false;
        }

        return eventRepository.archiveById(id);
    }

    @Override
    public boolean restoreEvent(int id) {
        if (id <= 0) {
            System.out.println("\nValidation Error: ID must be greater than 0.");
            return false;
        }

        return eventRepository.restoreById(id);
    }

    @Override
    public List<Event> searchEvents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("\nValidation Error: Search keyword is required.");
            return new ArrayList<>();
        }

        return eventRepository.searchByName(keyword.trim());
    }

    private boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("\nValidation Error: Event name is required.");
            return false;
        }

        if (name.trim().length() > 50) {
            System.out.println("\nValidation Error: Event name must be 50 characters or less.");
            return false;
        }

        return true;
    }
}