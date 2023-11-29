import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;


public class Controller extends year {

    int monthCounter = 0;

    @FXML
    private Button monthView;

    @FXML
    private Button weekView;

    @FXML
    private Button yearView;

    @FXML
    private TableView<?> table;


    @FXML
    void showMonth(ActionEvent event) throws IOException {
        Stage window = (Stage) monthView.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("monthView.fxml"));
            Scene scene = new Scene(root);
            window.setScene(scene);
            window.show();
    }

    @FXML
    void showWeek(ActionEvent event) throws IOException {
        Stage window = (Stage) monthView.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("weekView.fxml"));
            Scene scene = new Scene(root);
            window.setScene(scene);
            window.show();
    }

    @FXML
    void showYear(ActionEvent event) throws IOException {
        Stage window = (Stage) monthView.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("yearView.fxml"));
            Scene scene = new Scene(root);
            window.setScene(scene);
            window.show();
    }

    @FXML
    void jumpnext(ActionEvent event) throws IOException {
        monthCounter++;
    
        if(monthCounter > 11){
            monthCounter = 11;

        }
        else if (monthCounter < 0){
            monthCounter = 0;
        }

        Scene scene = (Scene) monthView.getScene();
        Label label = (Label) scene.lookup("#monthname");
        label.setText(monthName[monthCounter]);
    }

    @FXML
    void stepback(ActionEvent event){
        monthCounter--;

        if(monthCounter > 11){
            monthCounter = 11;
        }
        else if (monthCounter < 0){
            monthCounter = 0;
        }

        Scene scene = (Scene) monthView.getScene();
        Label label = (Label) scene.lookup("#monthname");
        label.setText(monthName[monthCounter]);
    }
}
