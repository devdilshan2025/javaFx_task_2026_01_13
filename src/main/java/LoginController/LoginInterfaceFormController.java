package LoginController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginInterfaceFormController {

    Stage stage = new Stage();

    @FXML
    private Button btnSignIn;

    @FXML
    private Button btnSignUp;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    void btnSignInOnAction(ActionEvent event) {

        String email = txtEmail.getText();

        if (!isValidGmail(email)) {
            new Alert(Alert.AlertType.ERROR,
                    "Email must end with @gmail.com").show();

        }


    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) {

        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/SignUp_form.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();

    }


    private boolean isValidGmail(String email) {

        return email != null && email.endsWith("@gmail.com");
    }

}
