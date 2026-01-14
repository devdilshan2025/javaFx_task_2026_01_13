package SignupController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.PasswordUtil;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpFormController {


    private SignUpservice signUpservice = new SignupController();

    private Stage stage = new Stage();

    @FXML
    private Button btnBacktoLogin;

    @FXML
    private Button btnRegister;

    @FXML
    private TextField txtEmailAddres;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtReenterPassword;

    @FXML
    private TextField txtfirstName;

    // ================= BACK TO LOGIN =================
    @FXML
    void btnBacktoLoginOnAction(ActionEvent event) {
        try {
            stage.setScene(new Scene(
                    FXMLLoader.load(getClass().getResource("/view/LoginInterface_form.fxml"))
            ));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("System Error", "Unable to load login form");
        }
    }

    // ================= REGISTER =================
    @FXML
    void btnRegisterOnAction(ActionEvent event) {

        String firstName = txtfirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmailAddres.getText();
        String password = txtPassword.getText();
        String rePassword = txtReenterPassword.getText();

        //  Empty field validation
        if (firstName == null || firstName.trim().isEmpty() ||
                lastName == null || lastName.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                password == null || password.isEmpty() ||
                rePassword == null || rePassword.isEmpty()) {

            showAlert("Validation Error", "All fields are required");
            return;
        }

        // Email validation
        if (!isValidGmail(email)) {
            showAlert("Invalid Email", "Email must end with @gmail.com");
            return;
        }

        //  Password match validation
        if (!password.equals(rePassword)) {
            showAlert(
                    "Password Error",
                    "Password and Re-enter Password must match!"
            );
            return;
        }

        //  Password strength validation (FIXED REGEX)
        String passwordRegex =
                "^(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9]).{8,}$";

        if (!password.matches(passwordRegex)) {
            showAlert(
                    "Weak Password",
                    "Password must contain:\n" +
                            "- At least 8 characters\n" +
                            "- One uppercase letter\n" +
                            "- One lowercase letter\n" +
                            "- One special character"
            );
            return;
        }

        //  Hash password
        String hashedPassword = PasswordUtil.hashPassword(password);

        // Save user
        try {
            signUpservice.addausers(
                    firstName,
                    lastName,
                    email,
                    hashedPassword
            );

            showAlert("Success", "Account created successfully!");

            // Optional: go back to login
            stage.setScene(new Scene(
                    FXMLLoader.load(getClass().getResource("/view/LoginInterface_form.fxml"))
            ));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Database Error", "Unable to create account");
        }
    }

    // ================= UTIL =================
    private boolean isValidGmail(String email) {
        return email.endsWith("@gmail.com");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}