package io.blackjesus.calendario.controllers;

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
    public DayController(int year, int month, int dayOfMonth, boolean renderAsCurrentMonth) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
        this.renderAsCurrentMonth = renderAsCurrentMonth;
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
                    Color.RED,
                    BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY,
                    BorderStroke.THIN
            ));
            container.setBorder(border);
        }
    }
}
