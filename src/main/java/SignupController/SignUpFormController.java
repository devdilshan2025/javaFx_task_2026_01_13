package SignupController;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import util.PasswordUtil;


import java.sql.SQLException;


public class SignUpFormController {

    SignUpservice signUpservice = new SignupController();

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

    @FXML
    void btnBacktoLoginOnAction(ActionEvent event) throws SQLException {

     signUpservice.addausers(txtfirstName.getText(),txtLastName.getText(),txtEmailAddres.getText(),txtPassword.getText());
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) throws SQLException {

        String firstName = txtfirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmailAddres.getText();
        String password = txtPassword.getText();
        String rePassword = txtReenterPassword.getText();


        if (!isValidGmail(email)) {
            new Alert(Alert.AlertType.ERROR,
                    "Email must end with @gmail.com").show();
            return;

        }

        if (!password.equals(rePassword)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Password Error");
            alert.setHeaderText(null);
            alert.setContentText("Password and Re-enter Password must match!");
            alert.show();
            return;
        }

         String pass = txtPassword.getText();
         String hashPassword = PasswordUtil.hashPassword(pass);

         signUpservice.addausers(txtfirstName.getText(),txtLastName.getText(),txtEmailAddres.getText(),hashPassword);





    }

    private boolean isValidGmail(String email) {

        return email != null && email.endsWith("@gmail.com");
    }




}
