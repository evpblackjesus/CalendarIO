package io.blackjesus.calendario.controllers;

import io.blackjesus.calendario.managers.EventManager;
import io.blackjesus.calendario.models.CalendarEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class DailyViewController {

    @FXML
    private Text yearmonth;

    @FXML
    private Text day;

    @FXML
    private Text dayname;

    @FXML
    private DatePicker datePicker;

    private LocalDate date;

    @FXML
    private VBox eventsContainer;


    // Az aktuális év és hónap, nap, nap neve
    private String currentYearMonth;
    private int currentDay;
    private String currentDayName;

    // Egy HashMap a napnevek fordításához
    private static final HashMap<String, String> DAY_NAME_MAP = new HashMap<>();

    static {
        DAY_NAME_MAP.put("MONDAY", "Hétfő");
        DAY_NAME_MAP.put("TUESDAY", "Kedd");
        DAY_NAME_MAP.put("WEDNESDAY", "Szerda");
        DAY_NAME_MAP.put("THURSDAY", "Csütörtök");
        DAY_NAME_MAP.put("FRIDAY", "Péntek");
        DAY_NAME_MAP.put("SATURDAY", "Szombat");
        DAY_NAME_MAP.put("SUNDAY", "Vasárnap");
    }

    // Angol napnevet fordít magyarra
    public static String convertToHungarian(String englishDayName) {
        return DAY_NAME_MAP.getOrDefault(englishDayName.toUpperCase(), "Ismeretlen nap");
    }

    // Megadott hónapszám alapján visszaadja a rövidített hónapnevet
    public String getShortMonthName(int monthNumber) {
        if (monthNumber < 1 || monthNumber > 12) {
            throw new IllegalArgumentException("Érvénytelen hónap száma. Megadott érték: " + monthNumber);
        }

        String[] monthNames = {"", "jan", "feb", "már", "ápr", "máj", "jún", "júl", "aug", "szept", "okt", "nov", "dec"};

        return monthNames[monthNumber];
    }

    // Inicializáló metódus, itt lehet beállítani például az eseményfigyelőket
    @FXML
    void initialize() {
        // Eseményfigyelő hozzáadása a DatePicker-hez
        datePicker.setOnAction(e -> handleDatePickerAction());
        if (yearmonth.getText().equals("") && day.getText().equals("") && dayname.getText().equals("")){
            setDate(LocalDate.now());
            date = LocalDate.now();
        }

    }

    // Gombnyomásra az aktuális dátumot beállító metódus
    @FXML
    void nowButton(ActionEvent event) {
        LocalDate currentDate = LocalDate.now();
        setDate(currentDate);
        date = currentDate;
    }

    // Eseményfigyelő a DatePicker-hez, amikor új dátumot választunk ki
    private void handleDatePickerAction() {
        LocalDate selectedDate = datePicker.getValue();

        if (selectedDate != null) {
            setDate(selectedDate);
            date = selectedDate;
        }
    }

    // Dátum beállító metódus
    void setDate(LocalDate setdate) {
        eventsContainer.getChildren().clear();

        currentYearMonth = setdate.getYear() + " " + getShortMonthName(setdate.getMonthValue());

        currentDay = setdate.getDayOfMonth();

        currentDayName = setdate.getDayOfWeek().toString();


        //Eventek hozzáadása
        List<CalendarEvent> events = EventManager.getEventsOnDate(setdate);
        if(!events.isEmpty()) {
            int i = 0;
            while(i < 3 && i < events.size()) {
                HBox eventContainer = createCalendarEventBox(events.get(i));
                eventsContainer.getChildren().add(eventContainer);
                i++;
            }
        }

        // Értékek beállítása a felületen
        yearmonth.setText(currentYearMonth);
        day.setText(String.valueOf(currentDay));
        dayname.setText(convertToHungarian(currentDayName));
    }

    private HBox createCalendarEventBox(CalendarEvent calendarEvent) {
        HBox container = new HBox();
        container.setCursor(Cursor.HAND);
        String bgColor = "";
        switch (calendarEvent.getType()) {
            case EVENT -> bgColor = "rgb(121,134,203)";
            case TASK -> bgColor = "rgb(66, 133, 244)";
            case REMINDER -> bgColor = "rgb(142,36,170)";
            default -> bgColor = "BLACK";
        }
        container.setPadding(new Insets(5, 10, 5, 10));

        container.setStyle("-fx-background-color: " + bgColor + "; -fx-background-radius: 15;");


        Label titleLabel = new Label();
        titleLabel.setText(calendarEvent.getTitle());
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-strikethrough: true; -fx-font-size: 21px;");

        container.getChildren().add(titleLabel);
        return container;
    }

}
