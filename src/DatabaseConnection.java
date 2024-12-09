import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Centralized database connection class
public class DatabaseConnection {
    // Database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employeeData";
    private static final String USER = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "Pokelover!2"; // Replace with your MySQL password

    // Method to establish and return a connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}

/* Usage example:
try (Connection myConn = DatabaseConnection.getConnection()) {
    Statement myStmt = myConn.createStatement();
    ...[insert rest of code here]
}
*/
