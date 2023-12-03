package io.blackjesus.calendario.controllers;

import io.blackjesus.calendario.managers.PageManager;
import javafx.event.ActionEvent;

public class YearViewController {
    public void show(ActionEvent actionEvent) {
        PageManager.switchPage("weekly");
    }
}
