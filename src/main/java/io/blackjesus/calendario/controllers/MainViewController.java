package io.blackjesus.calendario.controllers;

import io.blackjesus.calendario.enums.CalendarEventType;
import io.blackjesus.calendario.managers.EventManager;
import io.blackjesus.calendario.managers.PageManager;
import io.blackjesus.calendario.models.CalendarEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    private static MainViewController mainViewController;

    public static MainViewController getInstance() {
        return mainViewController;
    }

    @FXML
    private StackPane root;

    @FXML
    private TextField eventstext;

    @FXML
    private VBox eventsbox;

    @FXML
    public DatePicker datepicker;

    @FXML
    public ComboBox<String> typeCmb;

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
        LocalDate eventDate = datepicker.getValue();
        CalendarEventType eventType = CalendarEventType.getEventType(typeCmb.getValue());

        if (!eventName.isEmpty()) {
            CalendarEvent event = new CalendarEvent(eventName, eventDate, eventType, false);
            EventManager.addEvent(event);
            updateEventList();
        }
    }

    public void updateEventList() {
        eventsbox.getChildren().clear();
        for (CalendarEvent event : EventManager.events) {
            HBox eventBox = createCalendarEventBox(event);
            eventsbox.getChildren().add(eventBox);
        }
    }

    private HBox createCalendarEventBox(CalendarEvent calendarEvent) {
        HBox container = new HBox();
        container.setCursor(Cursor.HAND);
        String bgColor = calendarEvent.getEventColor();
        container.setStyle("-fx-background-color: " + bgColor + "; -fx-background-radius: 6;");
        container.setPadding(new Insets(0, 5, 0, 5));

        Label titleLabel = new Label();
        titleLabel.setMinWidth(145);
        titleLabel.setMaxWidth(145);
        titleLabel.setText(calendarEvent.getTitle());
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-font-size: 18");

        HBox dateContainer = new HBox();
        dateContainer.setAlignment(Pos.CENTER_RIGHT);
        dateContainer.setPrefWidth(200);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy. MM dd.", new Locale("hu", "HU"));
        Label dateLabel = new Label(calendarEvent.getDate().format(formatter));
        dateLabel.setTextFill(Color.WHITE);
        dateLabel.setStyle("-fx-font-size: 14");

        dateContainer.getChildren().add(dateLabel);

        container.setOnMouseClicked(event -> {
            Parent parent = PageManager.loadFxml("event-modify-view", param -> new EventModifyViewController(calendarEvent, "monthly"));
            PageManager.switchPage(parent);
        });

        container.getChildren().add(titleLabel);
        container.getChildren().add(dateContainer);
        return container;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (mainViewController == null) {
            mainViewController = this;
        }
        PageManager.setMainViewController(this);

        for (CalendarEventType type : CalendarEventType.values()) {
            typeCmb.getItems().add(type.getDisplayValue());
        }
        typeCmb.setValue(CalendarEventType.EVENT.getDisplayValue());

        updateEventList();
    }
}
