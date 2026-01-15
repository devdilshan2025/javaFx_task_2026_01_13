package LoginController;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginInterfaceController implements LoginService {

    @Override
    public ResultSet getPasswordByEmail(String email) throws SQLException {

        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT password FROM users WHERE email = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        return rs;
    }

    @Override
    public ResultSet getFirstNameByEmail(String email) throws SQLException {

        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT first_name FROM users WHERE email = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        return  rs;
    }

}
