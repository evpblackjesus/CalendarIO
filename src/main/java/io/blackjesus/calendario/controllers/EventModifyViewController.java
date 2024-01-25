package io.blackjesus.calendario.controllers;

import io.blackjesus.calendario.enums.CalendarEventType;
import io.blackjesus.calendario.managers.EventManager;
import io.blackjesus.calendario.managers.PageManager;
import io.blackjesus.calendario.models.CalendarEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EventModifyViewController implements Initializable {
    private CalendarEvent event;
    private String pageName;
    private Node page;

    public EventModifyViewController(CalendarEvent event, String pageName) {
        this.event = event;
        this.pageName = pageName;
    }

    public EventModifyViewController(CalendarEvent event, Node page) {
        this.event = event;
        this.page = page;
    }

    @FXML
    private TextField eventTitle;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> typeCmb;

    @FXML
    private CheckBox completedChb;

    @FXML
    public void saveEvent() {
        event.setTitle(eventTitle.getText());
        event.setDate(datePicker.getValue());
        event.setCompleted(completedChb.isSelected());
        event.setType(CalendarEventType.getEventType(typeCmb.getValue()));

        if (event.isCompleted()) {
            EventManager.removeEvent(event.getUuid());
        }

        //Visszaváltás előtt frissíteni kell minden nézetet, hogy megváltozzon a megjelenése
        MonthlyViewController.getInstance().updateCalendar();
        DailyViewController.getInstance().setDate(event.getDate());
        MainViewController.getInstance().updateEventList();

        PageManager.switchPage(pageName);
    }

    @FXML
    public void cancelEvent() {
        PageManager.switchPage(pageName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (CalendarEventType type : CalendarEventType.values()) {
            typeCmb.getItems().add(type.getDisplayValue());
        }
        typeCmb.setValue(event.getType().getDisplayValue());
        eventTitle.setText(event.getTitle());
        datePicker.setValue(event.getDate());
        completedChb.setSelected(event.isCompleted());
    }
}
