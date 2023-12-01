package io.blackjesus.calendario.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class DayController implements Initializable {
    private int year, month, dayOfMonth;
    public DayController(int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }
    @FXML
    private Label dayOfMonthLabel;

    @FXML
    private VBox eventsContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dayOfMonthLabel.setText(String.valueOf(dayOfMonth));
    }
}
