package io.blackjesus.calendario.enums;

public enum CalendarEventType {
    EVENT("Esemény"),
    TASK("Feladat"),
    REMINDER("Emlékeztető"),
    CONFERENCE("Konferencia"),
    PARTY("Szórakozás"),
    MEETING("Találkozó");

    private final String displayValue;

    CalendarEventType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public static CalendarEventType getEventType(String displayName) {
        if (displayName.equals("Esemény")) {
            return EVENT;
        } else if (displayName.equals("Feladat")) {
            return TASK;
        } else if (displayName.equals("Emlékeztető")) {
            return REMINDER;
        } else if (displayName.equals("Konferencia")) {
            return CONFERENCE;
        } else if (displayName.equals("Szórakozás")) {
            return PARTY;
        } else if (displayName.equals("Találkozó")) {
            return MEETING;
        } else {
            return null;
        }
    }
}
