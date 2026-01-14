package LoginController;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static util.PasswordUtil.hashPassword;

public class LoginInterfaceFormController {

    private Stage stage = new Stage();

    @FXML
    private Button btnSignIn;

    @FXML
    private Button btnSignUp;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    // ================= SIGN IN =================
    @FXML
    void btnSignInOnAction(ActionEvent event) {

        String email = txtEmail.getText();
        String enteredPassword = txtPassword.getText();

        //  Email empty check
        if (email == null || email.trim().isEmpty()) {
            showAlert("Validation Error", "Email cannot be empty");
            return;
        }

        //  Email format check
        if (!isValidGmail(email)) {
            showAlert("Invalid Email", "Email must end with @gmail.com");
            return;
        }

        //  Password empty check
        if (enteredPassword == null || enteredPassword.trim().isEmpty()) {
            showAlert("Validation Error", "Password cannot be empty");
            return;
        }

        //  Get password from DB
        String dbHashedPassword = getPasswordByEmail(email);

        if (dbHashedPassword == null) {
            showAlert("User Not Found", "Please Sign Up first");
            return;
        }

        //  Verify password
        if (!verifyPassword(enteredPassword, dbHashedPassword)) {
            showAlert("Login Failed", "Invalid password");
            return;
        }

        //  Login success → load dashboard
        try {
            stage.setScene(new Scene(
                    FXMLLoader.load(getClass().getResource("/view/Dashboard_form.fxml"))
            ));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("System Error", "Unable to load dashboard");
        }
    }

    // ================= SIGN UP =================
    @FXML
    void btnSignUpOnAction(ActionEvent event) {
        try {
            stage.setScene(new Scene(
                    FXMLLoader.load(getClass().getResource("/view/SignUp_form.fxml"))
            ));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("System Error", "Unable to load sign up form");
        }
    }

    // ================= VALIDATIONS =================
    private boolean isValidGmail(String email) {
        return email.endsWith("@gmail.com");
    }

    // ================= DB =================
    public String getPasswordByEmail(String email) {

        String sql = "SELECT password FROM users WHERE email = ?";

        try (
                Connection con = DBConnection.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("password");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ================= ALERT =================
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // ================= PASSWORD =================
    public boolean verifyPassword(String entered, String storedHash) {
        String enteredHash = hashPassword(entered);
        return enteredHash.equals(storedHash);
    }
}