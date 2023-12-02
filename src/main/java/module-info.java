module io.blackjesus.calendario.calendario {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens io.blackjesus.calendario to javafx.fxml;
    exports io.blackjesus.calendario;
    exports io.blackjesus.calendario.controllers;
    opens io.blackjesus.calendario.controllers to javafx.fxml;
    exports io.blackjesus.calendario.models;
}