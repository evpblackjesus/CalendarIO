package io.blackjesus.calendario.models;

import io.blackjesus.calendario.enums.CalendarEventType;

import java.time.LocalDate;
import java.util.UUID;

public class CalendarEvent {
    private String uuid;
    private String title;
    private LocalDate date;
    private CalendarEventType type;
    private boolean isCompleted;

    public CalendarEvent(String title, LocalDate date, CalendarEventType type, boolean isCompleted) {
        this.uuid = UUID.randomUUID().toString();
        this.type = type;
        this.date = date;
        this.isCompleted = isCompleted;
        this.title = title;
    }

    public CalendarEvent(String uuid, String title, LocalDate date, CalendarEventType type, boolean isCompleted) {
        this.uuid = uuid;
        this.title = title;
        this.date = date;
        this.type = type;
        this.isCompleted = isCompleted;
    }

    public String getEventColor() {
        String bgColor = "";
        switch (type) {
            case EVENT -> bgColor = "rgb(121,134,203)";
            case TASK -> bgColor = "rgb(66, 133, 244)";
            case REMINDER -> bgColor = "rgb(142,36,170)";
            case CONFERENCE -> bgColor = "rgb(51,182,121)";
            case PARTY -> bgColor = "rgb(246,191,38)";
            case MEETING -> bgColor = "rgb(130, 16, 1)";
            default -> bgColor = "BLACK";
        }
        return bgColor;
    }

    @Override
    public String toString() {
        return uuid + ";" + title + ";" + date.toString() + ";" + type + ";" + isCompleted;
    }

    public String getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public CalendarEventType getType() {
        return type;
    }

    public void setType(CalendarEventType type) {
        this.type = type;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
