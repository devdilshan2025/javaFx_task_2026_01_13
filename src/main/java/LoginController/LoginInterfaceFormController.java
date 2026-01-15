package LoginController;

import DashbordController.DashboardFormController;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static util.PasswordUtil.hashPassword;

public class LoginInterfaceFormController {

    private LoginService loginService = new LoginInterfaceController();

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

        // Email empty
        if (email == null || email.trim().isEmpty()) {
            showAlert("Validation Error", "Email cannot be empty");
            return;
        }

        // Email format
        if (!isValidGmail(email)) {
            showAlert("Invalid Email", "Email must end with @gmail.com");
            return;
        }

        // Password empty
        if (enteredPassword == null || enteredPassword.trim().isEmpty()) {
            showAlert("Validation Error", "Password cannot be empty");
            return;
        }

        // DB password
        String dbHashedPassword = getPasswordByEmail(email);
        if (dbHashedPassword == null) {
            showAlert("User Not Found", "Please Sign Up first");
            return;
        }

        // Verify
        if (!verifyPassword(enteredPassword, dbHashedPassword)) {
            showAlert("Login Failed", "Invalid password");
            return;
        }

        // Get first name
        String firstName = getFirstNameByEmail(email);

        // Load dashboard ONCE (CORRECT WAY)
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/Dashboard_form.fxml")
            );

            Scene scene = new Scene(loader.load());

            DashboardFormController controller = loader.getController();
            controller.setFirstName(firstName);

            Stage stage = (Stage) btnSignIn.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("System Error", "Unable to load dashboard");
        }
    }

    // ================= SIGN UP =================
    @FXML
    void btnSignUpOnAction(ActionEvent event) {
        try {
            Stage stage = (Stage) btnSignUp.getScene().getWindow();
            stage.setScene(new Scene(
                    FXMLLoader.load(getClass().getResource("/view/SignUp_form.fxml"))
            ));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("System Error", "Unable to load sign up form");
        }
    }

    // ================= VALIDATION =================
    private boolean isValidGmail(String email) {
        return email.endsWith("@gmail.com");
    }

    // ================= DB =================
    private String getPasswordByEmail(String email) {
        try {
            ResultSet rs = loginService.getPasswordByEmail(email);
            if (rs.next()) {
                return rs.getString("password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFirstNameByEmail(String email) {

        String sql = "SELECT first_name FROM users WHERE email = ?";

        try (
                Connection con = DBConnection.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("first_name");
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
    private boolean verifyPassword(String entered, String storedHash) {
        String enteredHash = hashPassword(entered);
        return enteredHash.equals(storedHash);
    }
}
