package io.blackjesus.calendario.test;

import io.blackjesus.calendario.enums.CalendarEventType;
import io.blackjesus.calendario.models.CalendarEvent;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CalendarEventTest {

    @Test
    void testConstructorWithTitle() {
        CalendarEvent event = new CalendarEvent("Meeting", LocalDate.now(), CalendarEventType.MEETING, false);
        assertNotNull(event.getUuid());
        assertEquals("Meeting", event.getTitle());
        assertFalse(event.isCompleted());
    }

    @Test
    void testConstructorWithUuid() {
        CalendarEvent event = new CalendarEvent("12345", "Conference", LocalDate.now(), CalendarEventType.CONFERENCE, true);
        assertEquals("12345", event.getUuid());
        assertEquals("Conference", event.getTitle());
        assertTrue(event.isCompleted());
    }

    @Test
    void testToString() {
        CalendarEvent event = new CalendarEvent("Event123", "Party", LocalDate.now(), CalendarEventType.PARTY, true);
        String expected = "Event123;Party;" + LocalDate.now() + ";PARTY;true";
        assertEquals(expected, event.toString());
    }

    @Test
    void testSetTitle() {
        CalendarEvent event = new CalendarEvent("Task", LocalDate.now(), CalendarEventType.TASK, false);
        event.setTitle("New Task");
        assertEquals("New Task", event.getTitle());
    }

    @Test
    void testSetDate() {
        CalendarEvent event = new CalendarEvent("Party time", LocalDate.now(), CalendarEventType.PARTY, false);
        LocalDate newDate = LocalDate.now().plusDays(1);
        event.setDate(newDate);
        assertEquals(newDate, event.getDate());
    }

    @Test
    void testSetType() {
        CalendarEvent event = new CalendarEvent("Meeting", LocalDate.now(), CalendarEventType.MEETING, false);
        event.setType(CalendarEventType.PARTY);
        assertEquals(CalendarEventType.PARTY, event.getType());
    }

    @Test
    void testSetCompleted() {
        CalendarEvent event = new CalendarEvent("Task", LocalDate.now(), CalendarEventType.TASK, false);
        event.setCompleted(true);
        assertTrue(event.isCompleted());
    }
}