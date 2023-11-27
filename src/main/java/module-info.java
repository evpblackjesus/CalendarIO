module io.blackjesus.calendario.calendario {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens io.blackjesus.calendario to javafx.fxml;
    exports io.blackjesus.calendario;
}