package io.blackjesus.calendario.controllers;

import io.blackjesus.calendario.managers.EventManager;
import io.blackjesus.calendario.models.CalendarEvent;
import io.blackjesus.calendario.models.DayStyling;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import io.blackjesus.calendario.managers.PageManager;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;

import java.net.URL;
import java.util.Date;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class DayController implements Initializable {
    private LocalDate date;
    private final boolean renderAsCurrentMonth;
    private DayStyling style;
    public DayController(LocalDate renderedDate, boolean renderAsCurrentMonth, DayStyling styling) {
        this.date = renderedDate;
        this.renderAsCurrentMonth = renderAsCurrentMonth;
        this.style = styling;
    }
    @FXML
    private VBox dayOfMonthContainer;

    @FXML
    private Label dayOfMonthLabel;

    @FXML
    private VBox eventsContainer;

    @FXML
    private VBox container;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dayOfMonthLabel.setText(String.valueOf(date.getDayOfMonth()));
        Border border = new Border(new BorderStroke(
                Color.GRAY,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                style.getBorderWidths()
        ));
        container.setBorder(border);

        if(!renderAsCurrentMonth) {
            dayOfMonthLabel.setTextFill(Color.GRAY);
        }

        //A mai napot megjelöljük valamint paddingek beállítása, hogy minden egy vonalban legyen
        if(style.isToday()) {
            Circle circle = new Circle(12, Color.rgb(26, 115, 232));
            dayOfMonthLabel.setTextFill(Color.WHITE);
            StackPane stackPane = new StackPane(circle, dayOfMonthLabel);
            stackPane.setBackground(new Background(new BackgroundFill(
                    Color.TRANSPARENT,
                    CornerRadii.EMPTY,
                    Insets.EMPTY
            )));
            dayOfMonthContainer.getChildren().clear();
            dayOfMonthContainer.getChildren().add(stackPane);
        } else {
            dayOfMonthContainer.setPadding(new Insets(2,0, 0, 0));
            eventsContainer.setPadding(new Insets(5, 0, 0, 0));
        }

        //Felső sor magassága nagyobb, mivel a napokat itt adjuk hozzá
        if(style.isTopRow()) {
            container.setPrefHeight(115);
            Label dayLabel = new Label();
            dayLabel.setText(date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.of("hu")));
            dayOfMonthContainer.getChildren().add(0, dayLabel);
        } else {
            container.setPrefHeight(92);
        }

        //Eventek hozzáadása
        List<CalendarEvent> events = EventManager.getEventsOnDate(date);
        if(!events.isEmpty()) {
            int i = 0;
            while(i < 3 && i < events.size()) {
                HBox eventContainer = createCalendarEventBox(events.get(i));
                eventsContainer.getChildren().add(eventContainer);
                i++;
            }
        }
    }

    private HBox createCalendarEventBox(CalendarEvent calendarEvent) {
        HBox container = new HBox();
        container.setCursor(Cursor.HAND);
        String bgColor = "";
        switch (calendarEvent.getType()) {
            case EVENT -> bgColor = "rgb(121,134,203)";
            case TASK -> bgColor = "rgb(66, 133, 244)";
            case REMINDER -> bgColor = "rgb(142,36,170)";
            default -> bgColor = "BLACK";
        }
        container.setStyle("-fx-background-color: " + bgColor + "; -fx-background-radius: 6;");
        container.setPadding(new Insets(0, 5, 0, 5));

        Label titleLabel = new Label();
        titleLabel.setText(calendarEvent.getTitle());
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-strikethrough: true;");

        container.setOnMouseClicked(event -> {
            Parent parent = PageManager.loadFxml("event-modify-view", param -> new EventModifyViewController(calendarEvent, "monthly"));
            PageManager.switchPage(parent);
        });

        container.getChildren().add(titleLabel);
        return container;
    }
}
