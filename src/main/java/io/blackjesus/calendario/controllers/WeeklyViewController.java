package io.blackjesus.calendario.controllers;

import io.blackjesus.calendario.managers.PageManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.BufferedReader;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class WeeklyViewController implements Initializable {


    private int currentYear;
    private int currentMonth;
    private int currentWeek;

    private LocalDate localDate;


    /**
     * currentYear változónak setter metódusa, az év nem lehet korábbi 2000-nél és későbbi 20 évvel későbbel
     * @param year
     */
    private void setCurrentYear(int year) {
        if(year < 2000 || year > LocalDate.now().getYear() + 20) {
            return;
        }
        currentYear = year;
    }

    /**
     * currentMonth változónak a setter metódusa, a hónap nem lehet kisebb 1-nél valamint nagyobb 12-nél
     * @param month
     */
    private void setCurrentMonth(int month) {
        if(month < 1) {
            //Előző évre lépés
            setCurrentYear(currentYear - 1);
            setCurrentMonth(12);
            return;
        }
        if(month > 12) {
            //Következő évre lépés
            setCurrentYear(currentYear + 1);
            setCurrentMonth(1);
            return;
        }
        currentMonth = month;
    }

    /*
    public List<Integer> setCurrentWeek(int year, int month, int weekNumber) {
        List<Integer> days = new ArrayList<>();


        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);

        LocalDate lastDayOfMonth = firstDayOfMonth.withDayOfMonth(firstDayOfMonth.lengthOfMonth());

        int weeksInMonth = (int) ChronoUnit.WEEKS.between(firstDayOfMonth, lastDayOfMonth);

        System.out.printf(String.valueOf(weeksInMonth));
        if (weekNumber < 1) {
            setCurrentMonth(currentMonth-1);
            setCurrentWeek(year,month,weekNumber);

        } else if (weekNumber > weeksInMonth) {
            setCurrentMonth(currentMonth+1);
            setCurrentWeek(year,month,1);
        }

        LocalDate firstDayOfWeek = firstDayOfMonth.with(WeekFields.of(Locale.getDefault()).weekOfMonth(), weekNumber)
                .with(DayOfWeek.MONDAY);

        System.out.println("Year: " + year);
        System.out.println("Month: " + month);
        System.out.println("Week Number: " + weekNumber);
        System.out.println("First Day of Week: " + firstDayOfWeek);

        for(int i = 0; i<7; i++){
            days.add(firstDayOfWeek.plusDays(i).getDayOfMonth());
            System.out.printf(firstDayOfWeek.plusDays(i).getDayOfMonth()+", ");
        }


        System.out.printf(String.valueOf(firstDayOfWeek));

        currentWeek = weekNumber;

        return days;

    }*/

    /**
     * Navigáció az előző hétre
     */
    @FXML
    private void prevWeek(){
        if (localDate != null) {
            localDate=localDate.minusWeeks(1);
            updateWeeklyView();
            currentDateUpdate(startDate,endDate);
        }
    }
    /**
     * Navigáció a következő hétre
     */
    @FXML
    private void nextWeek(){
        if (localDate != null) {
            localDate=localDate.plusWeeks(1);
            updateWeeklyView();
            currentDateUpdate(startDate,endDate);
        }
    }
    /**
     * Megnyitja a havi nézetet a jelenlegi hét évével és hónapjával
     */
    @FXML
    private void openMonthlyViewButton(){
        openMonthlyView(startDate.getYear(),startDate.getMonthValue());
    }
    /**
     * Frissíti a heti nézetet a jelenlegi dátumra
     */
    @FXML
    private void nowButton(){
        start(LocalDate.now());
    }

    @FXML
    private GridPane weeklyViewGrid;

    private static LocalDate startDate;
    private static LocalDate endDate;
    @FXML
    private Text currentDate;
    @FXML
    private Text currentDateMonth;


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
            Text dayText = new Text(String.valueOf(days.get(i)));
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

        //System.out.printf(startDate+" "+endDate);

        return days;
    }

    /**
     * A megadott dátum alapján idul el
     */
    public void start(LocalDate date) {
        localDate = date;
        updateWeeklyView();
        daysOfWeek(date);
        currentDateUpdate(startDate,endDate);

    }

    /**
     * Frissíti a két szövegmezőt
     */
    private void currentDateUpdate(LocalDate start, LocalDate end) {

        String formattedStartDate = "";
        String formattedEndDate = "";
        DateTimeFormatter Monthformatter = DateTimeFormatter.ofPattern("MMMM");


        if (start.getMonth().equals(end.getMonth())){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy. MMMM dd");
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd");
             formattedStartDate = start.format(formatter);
             formattedEndDate = end.format(formatter2);
        } else if (start.getYear()==(end.getYear())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy. MMMM dd");
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MMMM dd");
             formattedStartDate = start.format(formatter);
             formattedEndDate = end.format(formatter2);
        }else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy. MMMM dd");
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