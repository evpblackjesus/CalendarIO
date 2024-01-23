package io.blackjesus.calendario.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import java.time.LocalDate;
import java.util.HashMap;

public class DailyViewController {

    @FXML
    private Text yearmonth;

    @FXML
    private Text day;

    @FXML
    private Text dayname;

    @FXML
    private DatePicker datePicker;

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
        }
    }

    // Gombnyomásra az aktuális dátumot beállító metódus
    @FXML
    void nowButton(ActionEvent event) {
        LocalDate currentDate = LocalDate.now();
        setDate(currentDate);
    }

    // Eseményfigyelő a DatePicker-hez, amikor új dátumot választunk ki
    private void handleDatePickerAction() {
        LocalDate selectedDate = datePicker.getValue();

        if (selectedDate != null) {
            setDate(selectedDate);
        }
    }

    // Dátum beállító metódus
    void setDate(LocalDate date) {
        currentYearMonth = date.getYear() + " " + getShortMonthName(date.getMonthValue());

        currentDay = date.getDayOfMonth();

        currentDayName = date.getDayOfWeek().toString();

        // Értékek beállítása a felületen
        yearmonth.setText(currentYearMonth);
        day.setText(String.valueOf(currentDay));
        dayname.setText(convertToHungarian(currentDayName));
    }
}
