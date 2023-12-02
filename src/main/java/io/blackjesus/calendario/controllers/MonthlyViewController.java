package io.blackjesus.calendario.controllers;

import io.blackjesus.calendario.PageManager;
import io.blackjesus.calendario.models.DayStyling;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class MonthlyViewController implements Initializable {

    private int currentYear;

    //Nem index, tehát január: 1, február: 2 stb...
    private int currentMonth;

    /**
     * currentYear változónak setter metódusa, az év nem lehet korábbi 2000-nél és későbbi 20 évvel későbbel
     * @param year
     */
    private void setCurrentYear(int year) {
        if(year < 2000 || year > LocalDate.now().getYear() + 20) {
            return;
        }
        currentYear = year;
        currentYearLabel.setText(String.valueOf(year));
        updateCalendar();
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
        currentMonthLabel.setText(Month.of(month).getDisplayName(TextStyle.FULL, Locale.of("hu")));
        updateCalendar();
    }

    /**
     * Konstruktor inicializálja a kezdő értékeket
     */
    public MonthlyViewController() {
        LocalDate date = LocalDate.now();
        currentYear = date.getYear();
        currentMonth = date.getMonthValue();
    }

    @FXML
    private GridPane monthlyViewGrid;

    @FXML
    private Label currentYearLabel;

    @FXML
    private Label currentMonthLabel;

    @FXML
    private void onNextYearClick() {
        setCurrentYear(currentYear + 1);
    }

    @FXML
    private void onPrevYearClick() {
        setCurrentYear(currentYear - 1);
    }

    @FXML
    private void onNextMonthClick() {
        setCurrentMonth(currentMonth + 1);
    }

    @FXML
    private void onPrevMonthClick() {
        setCurrentMonth(currentMonth - 1);
    }

    /**
     * Frissíti a GUI-t az éppen aktuális értékek alapján
     */
    private void updateCalendar() {
        monthlyViewGrid.getChildren().clear();
        int[][] calendarMatrix = new int[6][7];
        fillCalendar(calendarMatrix, currentYear, currentMonth);
        boolean renderingCurrentMonth = false;
        for(int i = 0; i < calendarMatrix.length; i++) {
            int[] row = calendarMatrix[i];
            for (int j = 0; j < row.length; j++) {
                int day = row[j];
                if(day == 1) {
                    renderingCurrentMonth = !renderingCurrentMonth;
                }
                if (day != 0) { // Ellenőrizzük, hogy a nap létezik-e (nem null)
                    boolean finalRenderingCurrentMonth = renderingCurrentMonth;
                    DayStyling dayStyling = new DayStyling();
                    dayStyling.setTopRow(i == 0);
                    dayStyling.setLastColumn(j == row.length - 1);
                    Node node = PageManager.loadFxml("day", param -> new DayController(currentYear, currentMonth, day, finalRenderingCurrentMonth, dayStyling));
                    monthlyViewGrid.add(node, j, i);
                }
            }
        }
    }

    /**
     * Feltölti a paraméterben megkapott 6*7-es mátrixot a hónap napjaival
     * @param matrix
     * @param year
     * @param month
     */
    public void fillCalendar(int[][] matrix, int year, int month) {
        LocalDate currentDate = LocalDate.of(year, month, 1);

        // Előző hónap napjai
        LocalDate prevMonth = currentDate.minusMonths(1);
        int prevMonthLastDay = prevMonth.lengthOfMonth();
        int currentRow = 0;
        int prevMonthDay = prevMonthLastDay - currentDate.getDayOfWeek().getValue() + 2;
        for (int day = 1; day <= currentDate.getDayOfWeek().getValue(); day++) {
            matrix[currentRow][day - 1] = prevMonthDay;
            prevMonthDay++;
        }

        // Aktuális hónap napjai
        while (currentDate.getMonthValue() == month) {
            matrix[currentRow][currentDate.getDayOfWeek().getValue() - 1] = currentDate.getDayOfMonth();
            currentDate = currentDate.plusDays(1);
            if (currentDate.getDayOfWeek() == DayOfWeek.MONDAY) {
                currentRow++;
            }
        }

        // Következő hónap napjai
        int nextMonthDay = 1;
        while (currentRow < matrix.length) {
            matrix[currentRow][currentDate.getDayOfWeek().getValue() - 1] = nextMonthDay;
            currentDate = currentDate.plusDays(1);
            nextMonthDay++;
            if (currentDate.getDayOfWeek() == DayOfWeek.MONDAY) {
                currentRow++;
            }
        }
    }

    /**
     * Inicializáló metódus, ez az annotációval ellátott változók inicializálása után fut le
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentMonthLabel.setText(Month.of(currentMonth).getDisplayName(TextStyle.FULL, Locale.of("hu")));
        currentYearLabel.setText(String.valueOf(currentYear));
        updateCalendar();
    }
}
