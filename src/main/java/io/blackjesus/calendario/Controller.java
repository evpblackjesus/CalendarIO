package io.blackjesus.calendario;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class Controller {
    @FXML
    private StackPane root;

    private void switchContent(Node node) {
        if(!root.getChildren().isEmpty()) {
            root.getChildren().clear();
        }
        root.getChildren().add(node);
    }

    @FXML
    private void onDayViewClick() throws IOException {
        Label label = new Label("day view");
        switchContent(label);
    }

    @FXML
    private void onWeekViewClick() {
        switchContent(new Label("weekView"));
    }

    @FXML
    private void onMonthViewClick() {
        switchContent(new Label("monthView"));
    }
}
