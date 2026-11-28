package com.joysistvi.ribbons.repository;

import com.joysistvi.ribbons.model.Event;

import java.util.List;

public interface EventRepository {

    List<Event> findAllActive();

    List<Event> findAllArchived();

    Event findById(int id);

    Event findByName(String name);

    boolean save(Event event);

    boolean update(Event event);

    boolean archiveById(int id);

    boolean restoreById(int id);

    List<Event> searchByName(String keyword);
}