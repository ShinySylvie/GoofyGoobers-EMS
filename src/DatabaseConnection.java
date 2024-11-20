import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//so we only have to connect once just input your information
public class DatabaseConnection {

    // Database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employeeData";
    private static final String USER = "Insert user";
    private static final String PASSWORD = "Insert password";

    // Method to establish and return a connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}

/*you would just continue on forward like this:
try (Connection myConn = DriverManager.getConnection(url, user, password)) {
    Statement myStmt = myConn.createStatement();
    ...[insert rest of code here]
 */

