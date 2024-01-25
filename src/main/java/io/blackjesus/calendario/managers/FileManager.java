package io.blackjesus.calendario.managers;

import io.blackjesus.calendario.enums.CalendarEventType;
import io.blackjesus.calendario.models.CalendarEvent;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    public static String dataFolder = System.getenv("APPDATA") + File.separator + "Calendario";
    public static File dataFile = new File(dataFolder + File.separator + "data.dat");
    public static File folder = new File(dataFolder);

    public static void createDataFolder() {
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    public static void saveEvents() {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(dataFile), false);

            for (CalendarEvent event : EventManager.events) {
                pw.println(event.toString());
            }

            pw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<CalendarEvent> loadEvents() {
        List<CalendarEvent> events = new ArrayList<>();
        if (!dataFile.exists()) {
            return events;
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dataFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(";");
                CalendarEvent event = new CalendarEvent(split[0], split[1], LocalDate.parse(split[2]), CalendarEventType.valueOf(split[3]), Boolean.parseBoolean(split[4]));
                events.add(event);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return events;
    }
}
