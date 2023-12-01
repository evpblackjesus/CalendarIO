package io.blackjesus.calendario.controllers;

import io.blackjesus.calendario.PageManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private StackPane root;

    /**
     * Átváltja az oldalt a paraméterben átadott Node-ra
     * @param node
     */
    public void switchContent(Node node) {
        if(!root.getChildren().isEmpty()) {
            root.getChildren().clear();
        }
        root.getChildren().add(node);
    }

    @FXML
    private void onDayViewClick() {
        switchContent(PageManager.pages.get("daily"));
    }

    @FXML
    private void onWeekViewClick() {
        switchContent(PageManager.pages.get("weekly"));
    }

    @FXML
    private void onMonthViewClick() {
        switchContent(PageManager.pages.get("monthly"));
    }

    @FXML
    private void onYearViewClick() {
        switchContent(PageManager.pages.get("yearly"));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PageManager.setMainViewController(this);
    }
}
