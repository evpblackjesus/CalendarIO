package io.blackjesus.calendario.managers;

import io.blackjesus.calendario.enums.CalendarEventType;
import io.blackjesus.calendario.models.CalendarEvent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventManager {

    public static List<CalendarEvent> events = new ArrayList<>(Arrays.asList(
            new CalendarEvent("Kutya", LocalDate.of(2023, 12, 5), CalendarEventType.TASK, false),
            new CalendarEvent("Meló", LocalDate.of(2023, 12, 2), CalendarEventType.REMINDER, true),
            new CalendarEvent("Pakpak", LocalDate.of(2023, 12, 12), CalendarEventType.REMINDER, false),
            new CalendarEvent("Pakpak", LocalDate.of(2023, 12, 3), CalendarEventType.REMINDER, false),
            new CalendarEvent("Pakpak", LocalDate.of(2023, 12, 3), CalendarEventType.REMINDER, false),
            new CalendarEvent("Pakpak", LocalDate.of(2023, 12, 3), CalendarEventType.REMINDER, false),
            new CalendarEvent("Pakpak", LocalDate.of(2023, 12, 3), CalendarEventType.REMINDER, false),
            new CalendarEvent("DIMAT", LocalDate.of(2023, 12, 4), CalendarEventType.EVENT, false),
            new CalendarEvent("DIMAT", LocalDate.of(2023, 12, 4), CalendarEventType.EVENT, false),
            new CalendarEvent("DIMAT", LocalDate.of(2023, 12, 4), CalendarEventType.EVENT, false),
            new CalendarEvent("DIMAT", LocalDate.of(2023, 12, 4), CalendarEventType.EVENT, false)
    ));

    public static List<CalendarEvent> getEventsOnDate(LocalDate date) {
        List<CalendarEvent> foundEvents = new ArrayList<>();
        for(CalendarEvent event : events) {
            if(event.getDate().isEqual(date)) {
                foundEvents.add(event);
            }
        }
        return foundEvents;
    }
}
