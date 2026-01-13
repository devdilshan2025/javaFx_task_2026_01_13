package SignupController;

import java.sql.SQLException;

public interface SignUpservice {


    void addausers(String fName, String lName, String email, String password) throws SQLException;
}
