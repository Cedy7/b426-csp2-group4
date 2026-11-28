package com.joysistvi.ribbons.view;

import com.joysistvi.ribbons.model.Event;
import com.joysistvi.ribbons.service.EventService;

import java.util.List;

public class EventView {

    private final EventService eventService;

    public EventView(EventService eventService) {
        this.eventService = eventService;
    }

    public void handleMenu() {
        while (true) {
            ConsoleInput.clearScreen();
            System.out.println("\n=== MANAGE EVENTS ===");
            System.out.println("1. View All Active Events");
            System.out.println("2. Add Event");
            System.out.println("3. Update Event");
            System.out.println("4. Search Events");
            System.out.println("5. Archive Event");
            System.out.println("6. Restore Archived Event");
            System.out.println("0. Back");

            int choice = ConsoleInput.readInt("Choose an option: ");
            switch (choice) {
                case 1 -> displayAllEvents();
                case 2 -> promptCreateEvent();
                case 3 -> promptUpdateEvent();
                case 4 -> promptSearchEvents();
                case 5 -> promptArchiveEvent();
                case 6 -> promptRestoreEvent();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }

            ConsoleInput.pause();
        }
    }

    public void displayAllEvents() {
        List<Event> events = eventService.getAllActiveEvents();

        System.out.println("\n----- Events -----");
        printEvents(events);
    }

    public void promptCreateEvent() {
        System.out.println("\n----- Add Event -----");
        String name = ConsoleInput.readString("Enter event name: ");

        boolean created = eventService.createEvent(name);

        if (created) {
            System.out.println("\nEvent '" + name.trim() + "' created successfully!");
        } else {
            System.out.println("Failed to create event.");
        }
    }

    public void promptUpdateEvent() {
        displayAllEvents();
        System.out.println("\n----- Update Event -----");
        int id = ConsoleInput.readInt("Enter event ID to update: ");

        Event event = eventService.getEventById(id);

        if (event == null) {
            System.out.println("Event not found.");
            return;
        }

        System.out.println("Note: Press Enter without typing anything to keep the current value.");
        String name = ConsoleInput.readString("Enter new name [" + event.getName() + "]: ");

        if (name.trim().isEmpty()) {
            name = event.getName();
        }

        boolean updated = eventService.updateEvent(id, name);

        if (updated) {
            System.out.println("\nEvent ID " + id + " updated successfully!");
            displayAllEvents();
        } else {
            System.out.println("Failed to update event.");
        }
    }

    public void promptSearchEvents() {
        System.out.println("\n----- Search Events -----");
        String keyword = ConsoleInput.readString("Enter event name: ");

        if (keyword == null || keyword.trim().isEmpty()) {
            eventService.searchEvents(keyword);
            return;
        }

        List<Event> events = eventService.searchEvents(keyword);

        System.out.println("\n----- Search Events (Keyword: '" + keyword.trim() + "') -----");
        printEvents(events);
    }

    public void promptArchiveEvent() {
        displayAllEvents();
        System.out.println("\n----- Archive Event -----");
        int id = ConsoleInput.readInt("Enter event ID to archive: ");

        boolean archived = eventService.archiveEvent(id);

        if (archived) {
            System.out.println("\nEvent ID " + id + " archived successfully!");
            displayAllEvents();
        } else {
            System.out.println("Failed to archive event.");
        }
    }

    public void promptRestoreEvent() {
        System.out.println("\n----- Restore Event -----");

        List<Event> archivedEvents = eventService.getAllArchivedEvents();

        if (archivedEvents.isEmpty()) {
            System.out.println("No archived events found.");
            return;
        }

        System.out.println("\n----- Archived Events -----");
        printEvents(archivedEvents);

        int id = ConsoleInput.readInt("Enter event ID to restore: ");

        boolean restored = eventService.restoreEvent(id);

        if (restored) {
            System.out.println("\nEvent ID " + id + " restored successfully!");
            displayAllEvents();
        } else {
            System.out.println("Failed to restore event.");
        }
    }

    private void printEvents(List<Event> events) {
        if (events == null || events.isEmpty()) {
            System.out.println("No events found.");
            return;
        }

        String border = ConsoleInput.border(5, 30);

        System.out.println(border);
        System.out.printf("| %-5s | %-30s |%n", "ID", "Event Name");
        System.out.println(border);

        for (Event event : events) {
            System.out.printf("| %-5d | %-30s |%n",
                    event.getId(),
                    ConsoleInput.truncate(event.getName(), 30));
        }

        System.out.println(border);
    }
}