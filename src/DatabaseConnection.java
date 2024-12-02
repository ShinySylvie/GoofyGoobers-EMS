import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//so we only have to connect once just input your information
public class DatabaseConnection {

    // Database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employeeData";
    private static final String USER = "root";
    private static final String PASSWORD = "Pokelover!2";

    // Method to establish and return a connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}

/*you would just continue on forward like this:
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();) {
            [insert rest of code here]
    }
 */

