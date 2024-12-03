import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class deleteEmployee {

    public static void DeleteEmployee(Connection connection) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("\n--- Delete Employee ---");

            // Prompt user for the employee SSN
            System.out.print("Enter Employee SSN to delete: ");
            String ssn = scanner.nextLine();

            // SQL delete statement
            String sqlDelete = "DELETE FROM employees WHERE SSN = ?";

            // Prepare and execute the statement
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlDelete)) {
                preparedStatement.setString(1, ssn);

                int rowsDeleted = preparedStatement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Employee deleted successfully!");
                } else {
                    System.out.println("No employee found with the given SSN.");
                }
            }
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}

