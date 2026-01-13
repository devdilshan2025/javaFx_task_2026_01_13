package SignupController;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupController implements SignUpservice {


    @Override
    public void addausers(String fName, String lName, String email, String password) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO users (first_name,last_name,email,password) VALUES(?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setObject(1,fName);
        preparedStatement.setObject(2,lName);
        preparedStatement.setObject(3,email);
        preparedStatement.setObject(4,password);


        preparedStatement.executeUpdate();


    }
}
