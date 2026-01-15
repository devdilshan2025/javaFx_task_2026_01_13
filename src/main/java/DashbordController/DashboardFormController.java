package DashbordController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardFormController {

    private Stage stage = new Stage();

    @FXML
    private Button btnLogout;

    @FXML
    private Label lblfirstName;

    @FXML
    void btnLogoutOnAction(ActionEvent event) {

        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/LoginInterface_form.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();

    }

    public void setFirstName(String firstName) {
        lblfirstName.setText("Welcome, " + firstName);
    }

}
