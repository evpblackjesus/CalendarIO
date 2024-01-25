package io.blackjesus.calendario.controllers;

import io.blackjesus.calendario.managers.EventManager;
import io.blackjesus.calendario.managers.PageManager;
import io.blackjesus.calendario.models.CalendarEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class DailyViewController {

    private static DailyViewController dailyViewController;

    public static DailyViewController getInstance() {
        return dailyViewController;
    }

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

    public DailyViewController() {
        this.date = LocalDate.now();
    }

    // Inicializáló metódus, itt lehet beállítani például az eseményfigyelőket
    @FXML
    public void initialize() {
        if (dailyViewController == null) {
            dailyViewController = this;
        }

        // Eseményfigyelő hozzáadása a DatePicker-hez
        datePicker.setOnAction(e -> handleDatePickerAction());
        setDate(date);
    }

    @FXML
    private void prevDay() {
        date = date.minusDays(1);
        setDate(date);
    }

    @FXML
    private void nextDay() {
        date = date.plusDays(1);
        setDate(date);
    }

    // Gombnyomásra az aktuális dátumot beállító metódus
    @FXML
    private void nowButton(ActionEvent event) {
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
    public void setDate(LocalDate setdate) {
        date = setdate;
        eventsContainer.getChildren().clear();
        currentYearMonth = setdate.getYear() + " " + getShortMonthName(setdate.getMonthValue());
        currentDay = setdate.getDayOfMonth();
        currentDayName = setdate.getDayOfWeek().toString();

        //Eventek hozzáadása
        List<CalendarEvent> events = EventManager.getEventsOnDate(setdate);
        if (!events.isEmpty()) {
            for (CalendarEvent event : events) {
                HBox eventContainer = createCalendarEventBox(event);
                eventsContainer.getChildren().add(eventContainer);
            }
        } else {
            Label noEvent = new Label("Ezen a napon nincs esemény.");
            eventsContainer.getChildren().add(noEvent);
        }

        // Értékek beállítása a felületen
        yearmonth.setText(currentYearMonth);
        day.setText(String.valueOf(currentDay));
        dayname.setText(convertToHungarian(currentDayName));
    }

    private HBox createCalendarEventBox(CalendarEvent calendarEvent) {
        HBox container = new HBox();
        container.setCursor(Cursor.HAND);
        String bgColor = calendarEvent.getEventColor();
        container.setPadding(new Insets(5, 10, 5, 10));

        container.setStyle("-fx-background-color: " + bgColor + "; -fx-background-radius: 15;");

        container.setOnMouseClicked(event -> {
            Parent parent = PageManager.loadFxml("event-modify-view", param -> new EventModifyViewController(calendarEvent, "daily"));
            PageManager.switchPage(parent);
        });

        Label titleLabel = new Label();
        titleLabel.setText(calendarEvent.getTitle());
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-strikethrough: true; -fx-font-size: 21px;");

        container.getChildren().add(titleLabel);
        return container;
    }

}