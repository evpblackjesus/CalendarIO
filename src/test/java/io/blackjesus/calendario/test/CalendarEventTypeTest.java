package io.blackjesus.calendario.test;

import io.blackjesus.calendario.enums.CalendarEventType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CalendarEventTypeTest {

    @Test
    void testGetDisplayValue() {
        assertEquals("Esemény", CalendarEventType.EVENT.getDisplayValue());
        assertEquals("Feladat", CalendarEventType.TASK.getDisplayValue());
        assertEquals("Emlékeztető", CalendarEventType.REMINDER.getDisplayValue());
        assertEquals("Konferencia", CalendarEventType.CONFERENCE.getDisplayValue());
        assertEquals("Szórakozás", CalendarEventType.PARTY.getDisplayValue());
        assertEquals("Találkozó", CalendarEventType.MEETING.getDisplayValue());
    }

    @Test
    void testGetEventType() {
        assertEquals(CalendarEventType.EVENT, CalendarEventType.getEventType("Esemény"));
        assertEquals(CalendarEventType.TASK, CalendarEventType.getEventType("Feladat"));
        assertEquals(CalendarEventType.REMINDER, CalendarEventType.getEventType("Emlékeztető"));
        assertEquals(CalendarEventType.CONFERENCE, CalendarEventType.getEventType("Konferencia"));
        assertEquals(CalendarEventType.PARTY, CalendarEventType.getEventType("Szórakozás"));
        assertEquals(CalendarEventType.MEETING, CalendarEventType.getEventType("Találkozó"));
        // Ellenőrizzük, hogy a nem létező esemény típus esetén null-t ad vissza
        assertNull(CalendarEventType.getEventType("asd"));
    }
}