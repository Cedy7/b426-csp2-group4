package com.joysistvi.ribbons.service;

import com.joysistvi.ribbons.model.Event;

import java.util.List;

public interface EventService {

    List<Event> getAllActiveEvents();

    List<Event> getAllArchivedEvents();

    Event getEventById(int id);

    boolean createEvent(String name);

    boolean updateEvent(int id, String name);

    boolean archiveEvent(int id);

    boolean restoreEvent(int id);

    List<Event> searchEvents(String keyword);
}