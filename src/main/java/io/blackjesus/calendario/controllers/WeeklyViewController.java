package io.blackjesus.calendario.controllers;

import io.blackjesus.calendario.managers.EventManager;
import io.blackjesus.calendario.managers.PageManager;
import io.blackjesus.calendario.models.CalendarEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
            daysOfWeek(localDate);
            updateWeeklyView();
            currentDateUpdate(startDate, endDate);
            addEventToDate(daysOfWeek(localDate),columnBoxes);

        }
    }

    /**
     * Navigáció a következő hétre
     */
    @FXML
    private void nextWeek() {
        if (localDate != null) {
            localDate = localDate.plusWeeks(1);
            daysOfWeek(localDate);
            updateWeeklyView();
            currentDateUpdate(startDate, endDate);
            addEventToDate(daysOfWeek(localDate),columnBoxes);

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
    private List<VBox> columnBoxes;




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
        List<LocalDate> days = daysOfWeek(localDate);

        for (int i = 0; i < days.size(); i++) {
            Label dayText = new Label(String.valueOf(days.get(i).getDayOfMonth()));
            dayText.setStyle("-fx-font-size: 24");
            weeklyViewGrid.add(dayText, i, 1);
        }
    }



    /**
     * Visszaadja az adott héten található napok listáját
     */
    public static List<LocalDate> daysOfWeek(LocalDate date) {
        List<LocalDate> currentDaysList = new ArrayList<>();


        // Hétfőtől vasárnapig (1-től 7-ig)
        for (int i = 1; i <= 7; i++) {
            currentDaysList.add(date.with(DayOfWeek.of(i)));
            //System.out.println(currentDaysList.get(i-1));
        }
        startDate = date.with(DayOfWeek.of(1));
        endDate = date.with(DayOfWeek.of(7));

        return currentDaysList;
    }

    /**
     *
     * A megjelenő hét napjainak megfelelő eseményeket megjeleníti a megfelelő napok alatt
     */

    public void addEventToDate (List<LocalDate> currentDaysList, List<VBox> columnBoxes) {
    for (int i = 0; i < currentDaysList.size(); i++) {
        columnBoxes.get(i).getChildren().clear();
        //Eventek hozzáadása
        List<CalendarEvent> events = EventManager.getEventsOnDate(currentDaysList.get(i));
        if (!events.isEmpty()) {
            for (CalendarEvent event : events) {
                HBox eventContainer = createCalendarEventBox(event);
                System.out.printf(columnBoxes.get(i).toString());
                columnBoxes.get(i).getChildren().add(eventContainer);
            }
        }
    }
    }

    /**
     * Ezt nem használtam de frissítené az eventeket
     */
    public void updateEventList() {
        for (int i = 0; i < columnBoxes.size(); i++) {
            columnBoxes.get(i).getChildren().clear();
            for(CalendarEvent event : EventManager.events) {
                HBox eventBox = createCalendarEventBox(event);
                columnBoxes.get(i).getChildren().add(eventBox);
            }
        }

    }


    /**
     *
     * Létrehozza az eventboxot
     */
    private static HBox createCalendarEventBox(CalendarEvent calendarEvent) {
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

        container.setOnMouseClicked(event -> {
            Parent parent = PageManager.loadFxml("event-modify-view", param -> new EventModifyViewController(calendarEvent, "weekly"));
            PageManager.switchPage(parent);
        });

        Label titleLabel = new Label();
        titleLabel.setText(calendarEvent.getTitle());
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-font-size: 15px;");

        container.getChildren().add(titleLabel);
        return container;
    }

    /**
     * A megadott dátum alapján idul el
     */
    public void start(LocalDate date) {
        localDate = date;
        daysOfWeek(date);
        updateWeeklyView();
        currentDateUpdate(startDate, endDate);
        addEventToDate(daysOfWeek(date),columnBoxes);
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

        columnBoxes = Arrays.asList(columnBox1, columnBox2, columnBox3, columnBox4, columnBox5, columnBox6, columnBox7);
        start(LocalDate.now());
        updateWeeklyView();

    }
}