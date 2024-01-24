package io.blackjesus.calendario.controllers;

import io.blackjesus.calendario.managers.PageManager;
import io.blackjesus.calendario.controllers.MonthlyViewController;
import io.blackjesus.calendario.controllers.MainViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

// Az osztály definiálása
public class YearViewController {


    @FXML
    private Label yearLabel;

    @FXML
    private AnchorPane rootPane;

    private int currentYear;

    // Az év feliratának beállítása
    public void initialize() {
        LocalDate date = LocalDate.now();
        currentYear = date.getYear();
        yearLabel.setText(Integer.toString(currentYear));
    }

    // A következő év gomb eseménykezelője
    //year < 2000 || year > LocalDate.now().getYear() + 20
    public void onNextYearClick(ActionEvent actionEvent) {
        if (currentYear < LocalDate.now().getYear() + 20){
            currentYear++;
            yearLabel.setText(Integer.toString(currentYear));
        }
    }

    // Az előző év gomb eseménykezelője
    public void onPrevYearClick(ActionEvent actionEvent) {
        if (currentYear > 2000){
        currentYear--;
        yearLabel.setText(Integer.toString(currentYear));
        }
    }
    public void show(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        String id = source.getId();
        System.out.println(currentYear+" "+id);
        openMonthlyView(currentYear, Integer.parseInt(id));
    }

    /**
     * Az éves nézetből kiválasztott dátum alapján létrehozza a havi nézetet, majd kicseréli az oldat
     */
    public void openMonthlyView(int year, int month) {
        MonthlyViewController.getInstance().setDisplayedMonth(year, month);
        PageManager.switchPage("monthly");
    }
}
