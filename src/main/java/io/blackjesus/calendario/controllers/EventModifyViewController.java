package io.blackjesus.calendario.controllers;

import io.blackjesus.calendario.managers.PageManager;
import io.blackjesus.calendario.models.CalendarEvent;
import io.blackjesus.calendario.enums.CalendarEventType;
import io.blackjesus.calendario.managers.EventManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class EventModifyViewController implements Initializable {
    private CalendarEvent event;
    private String pageName;
    public EventModifyViewController(CalendarEvent event, String pageName) {
        this.event = event;
        this.pageName = pageName;
    }

    @FXML
    private TextField eventTitle;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<CalendarEventType> typeCmb;

    @FXML
    private CheckBox completedChb;


    @FXML
    public void saveEvent() {
        event.setTitle(eventTitle.getText());
        event.setDate(datePicker.getValue());
        event.setCompleted(completedChb.isSelected());
        event.setType(typeCmb.getValue());

        //Visszaváltás előtt frissíteni kell minden nézetet, hogy megváltozzon a megjelenése
        MonthlyViewController.getInstance().updateCalendar();
        DailyViewController.getInstance().setDate(event.getDate());

        if (event.isCompleted()){
            System.out.printf("completed");
            EventManager.removeEvent(eventTitle.getText(),datePicker.getValue());


        }else {
            System.out.printf("not completed");
        }

        PageManager.switchPage(pageName);
    }

    @FXML
    public void cancelEvent() {
        PageManager.switchPage(pageName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeCmb.getItems().setAll(CalendarEventType.values());
        typeCmb.setValue(event.getType());
        eventTitle.setText(event.getTitle());
        datePicker.setValue(event.getDate());
        completedChb.setSelected(event.isCompleted());
    }
}
