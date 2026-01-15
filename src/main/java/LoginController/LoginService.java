package LoginController;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface LoginService {

    ResultSet getPasswordByEmail(String email) throws SQLException;
}
