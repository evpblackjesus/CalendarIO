package io.blackjesus.calendario.controllers;

import io.blackjesus.calendario.managers.PageManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class WeeklyViewController implements Initializable {

    private LocalDate localDate;

    /**
     * Navigáció az előző hétre
     */
    @FXML
    private void prevWeek() {
        if (localDate != null) {
            localDate = localDate.minusWeeks(1);
            updateWeeklyView();
            currentDateUpdate(startDate, endDate);
        }
    }

    /**
     * Navigáció a következő hétre
     */
    @FXML
    private void nextWeek() {
        if (localDate != null) {
            localDate = localDate.plusWeeks(1);
            updateWeeklyView();
            currentDateUpdate(startDate, endDate);
        }
    }

    /**
     * Megnyitja a havi nézetet a jelenlegi hét évével és hónapjával
     */
    @FXML
    private void openMonthlyViewButton() {
        openMonthlyView(startDate.getYear(), startDate.getMonthValue());
    }

    /**
     * Frissíti a heti nézetet a jelenlegi dátumra
     */
    @FXML
    private void nowButton() {
        start(LocalDate.now());
    }

    @FXML
    private GridPane weeklyViewGrid;

    private static LocalDate startDate, endDate;

    @FXML
    private Text currentDate, currentDateMonth;

    @FXML
    private VBox columnBox1, columnBox2, columnBox3, columnBox4, columnBox5, columnBox6, columnBox7;


    /**
     * Beírja a Grid-be a megfelelő napokat
     */
    private void updateWeeklyView() {
        List<Node> nodesToRemove = new ArrayList<>();
        for (Node node : weeklyViewGrid.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            if (rowIndex != null && rowIndex == 1) {
                nodesToRemove.add(node);
            }
        }
        weeklyViewGrid.getChildren().removeAll(nodesToRemove);
        List<Integer> days = daysOfWeek(localDate);

        for (int i = 0; i < days.size(); i++) {
            Label dayText = new Label(String.valueOf(days.get(i)));
            dayText.setStyle("-fx-font-size: 24");
            weeklyViewGrid.add(dayText, i, 1);
        }
    }

    

    /**
     * Visszaadja az adott héten található napok listáját
     */
    public static List<Integer> daysOfWeek(LocalDate date) {
        List<Integer> days = new ArrayList<>();

        // Hétfőtől vasárnapig (1-től 7-ig)
        for (int i = 1; i <= 7; i++) {
            LocalDate currentDay = date.with(DayOfWeek.of(i));
            days.add(currentDay.getDayOfMonth());
        }
        startDate = date.with(DayOfWeek.of(1));
        endDate = date.with(DayOfWeek.of(7));

        return days;
    }

    /**
     * A megadott dátum alapján idul el
     */
    public void start(LocalDate date) {
        localDate = date;
        updateWeeklyView();
        daysOfWeek(date);
        currentDateUpdate(startDate, endDate);
    }

    /**
     * Frissíti a két szövegmezőt
     */
    private void currentDateUpdate(LocalDate start, LocalDate end) {
        String formattedStartDate = "";
        String formattedEndDate = "";
        DateTimeFormatter Monthformatter = DateTimeFormatter.ofPattern("MMMM");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy. MMMM dd");

        if (start.getMonth().equals(end.getMonth())) {
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd");
            formattedStartDate = start.format(formatter);
            formattedEndDate = end.format(formatter2);
        } else if (start.getYear() == (end.getYear())) {
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MMMM dd");
            formattedStartDate = start.format(formatter);
            formattedEndDate = end.format(formatter2);
        } else {
            formattedStartDate = start.format(formatter);
            formattedEndDate = end.format(formatter);
        }

        String merge = formattedStartDate + " - " + formattedEndDate;
        currentDate.setText(merge);
        currentDateMonth.setText(start.format(Monthformatter));
    }

    /**
     * Megnyitja a havi nézetet a megadott év és hónap alapján. (Évi nézetből másoltam)
     */
    public void openMonthlyView(int year, int month) {
        MonthlyViewController.getInstance().setDisplayedMonth(year, month);
        PageManager.switchPage("monthly");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        start(LocalDate.now());
        updateWeeklyView();
    }
}