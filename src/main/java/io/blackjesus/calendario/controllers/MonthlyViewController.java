package io.blackjesus.calendario.controllers;

import io.blackjesus.calendario.managers.PageManager;
import io.blackjesus.calendario.models.DayStyling;
import io.blackjesus.calendario.controllers.YearViewController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

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
        updateViewLabel();
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
        updateViewLabel();
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
    private Label currentViewLabel;

    @FXML
    private void onNextMonthClick() {
        setCurrentMonth(currentMonth + 1);
    }

    @FXML
    private void onPrevMonthClick() {
        setCurrentMonth(currentMonth - 1);
    }

    private void updateViewLabel() {
        currentViewLabel.setText(currentYear + ". " + Month.of(currentMonth).getDisplayName(TextStyle.FULL, Locale.of("hu")));
    }

    /**
     * A megadott év, hónap szerint tölti be a naptárt
     */
    public void setDisplayedMonth(int year, int month) {
        currentYear = year;
        currentMonth = month;
        updateViewLabel();
        updateCalendar();
    }

    /**
     * Frissíti a GUI-t az éppen aktuális értékek alapján
     */
    private void updateCalendar() {
        monthlyViewGrid.getChildren().clear();
        LocalDate[][] calendarMatrix = new LocalDate[6][7];
        fillCalendar(calendarMatrix, currentYear, currentMonth);

        boolean renderingCurrentMonth = false;
        for (int i = 0; i < calendarMatrix.length; i++) {
            LocalDate[] row = calendarMatrix[i];
            for (int j = 0; j < row.length; j++) {
                LocalDate date = row[j];
                if (date != null && date.getDayOfMonth() == 1) {
                    renderingCurrentMonth = !renderingCurrentMonth;
                }
                if (date != null) {
                    boolean finalRenderingCurrentMonth = renderingCurrentMonth;
                    DayStyling dayStyling = new DayStyling();
                    dayStyling.setTopRow(i == 0);
                    dayStyling.setLastColumn(j == row.length - 1);
                    dayStyling.setToday(date.isEqual(LocalDate.now()));
                    Node node = PageManager.loadFxml("day", param -> new DayController(date, finalRenderingCurrentMonth, dayStyling));
                    monthlyViewGrid.add(node, j, i);



                    // Ezen rész hozzáadása: Ha a gombra kattintanak, megjelenítjük a havi nézetet
                    if (node instanceof Button) {
                        Button button = (Button) node;
                        button.setOnAction(event -> {
                            int year = currentYear;
                            int month = currentMonth;
                            monthlyViewGrid.getChildren().add(node);
                        });
                    }
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
    public static void fillCalendar(LocalDate[][] matrix, int year, int month) {
        LocalDate currentDate = LocalDate.of(year, month, 1);

        // Előző hónap napjai
        LocalDate prevMonth = currentDate.minusMonths(1);
        int prevMonthLastDay = prevMonth.lengthOfMonth();
        int currentRow = 0;
        int prevMonthDay = prevMonthLastDay - currentDate.getDayOfWeek().getValue() + 2;
        for (int day = 1; day <= currentDate.getDayOfWeek().getValue(); day++) {
            matrix[currentRow][day - 1] = prevMonth.plusDays(prevMonthDay - 1);
            prevMonthDay++;
        }

        // Aktuális hónap napjai
        while (currentDate.getMonthValue() == month) {
            matrix[currentRow][currentDate.getDayOfWeek().getValue() - 1] = currentDate;
            currentDate = currentDate.plusDays(1);
            if (currentDate.getDayOfWeek() == DayOfWeek.MONDAY) {
                currentRow++;
            }
        }

        // Következő hónap napjai
        while (currentRow < matrix.length) {
            matrix[currentRow][currentDate.getDayOfWeek().getValue() - 1] = currentDate;
            currentDate = currentDate.plusDays(1);
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
        // Az év és hónap megjelenítése a naptáron
        setDisplayedMonth(currentYear, currentMonth);
    }
}
