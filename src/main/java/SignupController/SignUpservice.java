package SignupController;

import java.sql.SQLException;

public interface SignUpservice {
    void addausers();

    void addausers(String fname, String LName, String Email, String password) throws SQLException;
}
