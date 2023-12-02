package io.blackjesus.calendario.controllers;

import io.blackjesus.calendario.models.DayStyling;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class DayController implements Initializable {
    private int year, month, dayOfMonth;
    private boolean renderAsCurrentMonth;
    private DayStyling style;
    public DayController(int year, int month, int dayOfMonth, boolean renderAsCurrentMonth, DayStyling styling) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.renderAsCurrentMonth = renderAsCurrentMonth;
        this.style = styling;
    }
    @FXML
    private Label dayOfMonthLabel;

    @FXML
    private VBox eventsContainer;

    @FXML
    private VBox container;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dayOfMonthLabel.setText(String.valueOf(dayOfMonth));
        if(!renderAsCurrentMonth) {
            dayOfMonthLabel.setTextFill(Color.GRAY);
            Border border = new Border(new BorderStroke(
                    Color.GRAY,
                    BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY,
                    style.getBorderWidths()
            ));
            container.setBorder(border);
        } else {
            Border border = new Border(new BorderStroke(
                    Color.BLACK,
                    BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY,
                    style.getBorderWidths()
            ));
            container.setBorder(border);
        }
    }
}
