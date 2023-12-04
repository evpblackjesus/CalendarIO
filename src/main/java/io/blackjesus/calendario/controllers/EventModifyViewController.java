package io.blackjesus.calendario.controllers;

import io.blackjesus.calendario.managers.MonthlyViewManager;
import io.blackjesus.calendario.managers.PageManager;
import io.blackjesus.calendario.models.CalendarEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EventModifyViewController implements Initializable {

    private final CalendarEvent event;

    public EventModifyViewController(CalendarEvent event) {
        this.event = event;
    }

    @FXML
    public TextField titleTxf;

    @FXML
    public DatePicker dateDp;

    @FXML
    public TextField locationTxf;

    @FXML
    public CheckBox reminderChb;

    @FXML
    public TextArea descriptionTxa;

    private void switchBack(LocalDate viewDate) {
        PageManager.switchPage("monthly");
        MonthlyViewManager.setView(viewDate);
    }

    @FXML
    public void onCancelClick() {
        switchBack(event.getDate());
    }

    @FXML
    public void onSaveClick() {
        LocalDate viewDate = event.getDate();
        event.setTitle(titleTxf.getText());
        event.setDate(dateDp.getValue());
        event.setLocation(locationTxf.getText());
        event.setSendNotification(reminderChb.isSelected());
        event.setDescription(descriptionTxa.getText());
        switchBack(viewDate);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titleTxf.setText(event.getTitle());
        dateDp.setValue(event.getDate());
        locationTxf.setText(event.getLocation());
        reminderChb.setSelected(event.isSendNotification());
        descriptionTxa.setText(event.getDescription());
    }
}
