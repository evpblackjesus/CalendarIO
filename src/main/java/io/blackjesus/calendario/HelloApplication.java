package io.blackjesus.calendario;

import io.blackjesus.calendario.managers.EventManager;
import io.blackjesus.calendario.managers.FileManager;
import io.blackjesus.calendario.managers.PageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        PageManager.loadPages();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("views/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1220, 640);
        PageManager.switchPage("monthly");
        stage.setTitle("Calendario");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        FileManager.createDataFolder();
        EventManager.events.addAll(FileManager.loadEvents());
        launch();
    }
}