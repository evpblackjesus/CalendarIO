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

    protected int monthCounter = 0;

    @FXML
    protected Button jan;

    @FXML
    protected Button feb;

    @FXML
    protected Button mar;

    @FXML
    protected Button apr;

    @FXML
    protected Button maj;

    @FXML
    protected Button jun;

    @FXML
    protected Button home;

    @FXML
    protected Button monthView;

    @FXML
    protected Button weekView;

    @FXML
    protected Button yearView;

    @FXML
    protected TableView<?> table;


    @FXML
    void showMonth(ActionEvent event) throws IOException {
        Stage window = (Stage) monthView.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("monthView.fxml"));
            Scene scene = new Scene(root, 1000,600);
            window.setScene(scene);
            window.show();
    }

    @FXML
    void showWeek(ActionEvent event) throws IOException {
        Stage window = (Stage) monthView.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("weekView.fxml"));
            Scene scene = new Scene(root, 1000,600);
            window.setScene(scene);
            window.show();
    }

    @FXML
    void showYear(ActionEvent event) throws IOException {
        Stage window = (Stage) monthView.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("yearView.fxml"));
            Scene scene = new Scene(root, 1000,600);
            window.setScene(scene);
            window.show();
    }

    @FXML
    void homepage(ActionEvent event) throws IOException{
        Stage window = (Stage) monthView.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("mainscene.fxml"));
            Scene scene = new Scene(root, 1000,600);
            window.setScene(scene);
            window.show();
    }

    @FXML
    void jumpnext(ActionEvent event) throws IOException {
        monthCounter++;
        if(monthCounter > 11) monthCounter = 11;
        else if (monthCounter < 0) monthCounter = 0;
        Scene scene = (Scene) monthView.getScene();
        Label label = (Label) scene.lookup("#monthname");
        label.setText(monthName[monthCounter]);
    }

    @FXML
    void stepback(ActionEvent event){
        monthCounter--;
        if(monthCounter > 11) monthCounter = 11;
        else if (monthCounter < 0) monthCounter = 0;
        Scene scene = (Scene) monthView.getScene();
        Label label = (Label) scene.lookup("#monthname");
        label.setText(monthName[monthCounter]);
    }

    @FXML
    void show(ActionEvent event) throws IOException{
        String name = ((Button) event.getSource()).getId();
        int index = 0;
        while (!name.equals(monthNameShort[index])) index++;
        Stage window = (Stage) monthView.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("monthView.fxml"));
            Scene scene = new Scene(root, 1000,600);
            window.setScene(scene);
            window.show();
        Label label = (Label) scene.lookup("#monthname");
        label.setText(monthName[index]);
    }
}