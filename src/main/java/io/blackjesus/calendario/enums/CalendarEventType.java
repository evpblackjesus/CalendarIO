package io.blackjesus.calendario.enums;

public enum CalendarEventType {
    EVENT("Esemény"),
    TASK("Feladat"),
    REMINDER("Emlékeztető");

    private final String displayValue;

    CalendarEventType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public static CalendarEventType getEventType(String displayName) {
        if(displayName.equals("Esemény")) {
            return EVENT;
        } else if(displayName.equals("Feladat")) {
            return TASK;
        } else {
            return REMINDER;
        }
    }
}
