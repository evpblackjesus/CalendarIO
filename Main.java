import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{

    Stage window;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        window = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("mainscene.fxml"));
        Scene scene = new Scene(root);

        window.setTitle("CalendarIO");
        window.setScene(scene);
        window.show();


    }
}
