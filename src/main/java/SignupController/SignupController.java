package SignupController;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupController implements SignUpservice {


    @Override
    public void addausers() {

    }

    @Override
    public void addausers(String fname, String LName, String Email, String password) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO users VALUES(?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setObject(1,fname);
        preparedStatement.setObject(2,LName);
        preparedStatement.setObject(3,Email);
        preparedStatement.setObject(4,password);


        preparedStatement.executeUpdate();

    }
}
