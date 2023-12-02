package io.blackjesus.calendario.controllers;

import io.blackjesus.calendario.PageManager;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private StackPane root;
    @FXML
    private VBox eventsBox;
    @FXML
    private TextField eventstext;
    @FXML
    private Button addEventButton;
    @FXML
    private VBox eventsbox;
    @FXML
    private CheckBox checkBox;

    /**
     * Átváltja az oldalt a paraméterben átadott Node-ra
     *
     * @param node
     */
    public void switchContent(Node node) {
        if (!root.getChildren().isEmpty()) {
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

    //Hozzáadás funkció
    @FXML
    private void onEventAddClick() {
        String eventName = eventstext.getText();
        LocalDate eventDate = LocalDate.now();
        if (!eventName.isEmpty()) {
            CheckBox checkBox = new CheckBox();
            Label eventLabel = new Label(eventName);
            Label eventDateLabel = new Label(eventDate.toString());


            HBox eventHBox = new HBox(checkBox, eventLabel);
            eventHBox.setStyle("-fx-background-color: lightblue; -fx-border-color: black;");
            eventDateLabel.setPadding(new Insets(0, 0, 9, 0));

            //eseményfigyelő a CheckBox-hoz
            checkBox.setOnAction(event -> {
                // CheckBox ki van-e pipálva
                if (checkBox.isSelected()) {
                    //notification cuccos, ezt majd át akarom rakni az eventNotification() metódusba.
                    try {
                        //Obtain only one instance of the SystemTray object
                        SystemTray tray = SystemTray.getSystemTray();
                        Image image = Toolkit.getDefaultToolkit().createImage("some-icon.png");
                        //Alternative (if the icon is on the classpath):
                        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));
                        TrayIcon trayIcon = new TrayIcon(image, "Java AWT Tray Demo");
                        //Let the system resize the image if needed
                        trayIcon.setImageAutoSize(true);
                        //Set tooltip text for the tray icon
                        trayIcon.setToolTip("System tray icon demo");
                        tray.add(trayIcon);

                        // Display info notification:
                        trayIcon.displayMessage("Esemény elvégezve!", "Sikeresen elvégeztél egy eseményt!", TrayIcon.MessageType.INFO);
                        // Error:
                        // trayIcon.displayMessage("Hello, World", "Java Notification Demo", MessageType.ERROR);
                        // Warning:
                        // trayIcon.displayMessage("Hello, World", "Java Notification Demo", MessageType.WARNING);

                        //Töröljük az icon-t, hogy a program bezárás után ne fusson a háttérben
                        tray.remove(trayIcon);
                    } catch (Exception ex) {
                        System.err.print(ex.getMessage());
                    }
                    //strikethrough stílus
                    eventLabel.setStyle("-fx-strikethrough: true;");

                    //PauseTransition-t a várakozáshoz
                    PauseTransition pause = new PauseTransition(Duration.seconds(2)); // A 2 másodperces késleltetés

                    // eseménykezelő
                    pause.setOnFinished(e -> {
                        // HBox eltüntetése
                        eventsbox.getChildren().remove(eventHBox);
                        eventsbox.getChildren().remove(eventDateLabel);
                    });

                    pause.play();
                }
            });

            // Hbox hozzáadása eventsbox-hoz
            eventsbox.getChildren().add(eventHBox);
            eventsbox.getChildren().add(eventDateLabel);

            // szöveg törlése
            eventstext.clear();
        }
    }

    //majd külön notfication funkció lesz dátum szerint
    @FXML
    private void eventNotification() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PageManager.setMainViewController(this);
    }
}
