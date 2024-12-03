import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class addEmployee {

    public static void AddEmployee(Connection connection) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("\n--- Add New Employee ---");
            
            // Prompt for employee details
            System.out.print("Enter First Name: ");
            String fName = scanner.nextLine();

            System.out.print("Enter Last Name: ");
            String lName = scanner.nextLine();

            System.out.print("Enter Email: ");
            String email = scanner.nextLine();

            System.out.print("Enter Hire Date (YYYY-MM-DD): ");
            String hireDate = scanner.nextLine();

            System.out.print("Enter Salary: ");
            double salary = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            System.out.print("Enter SSN: ");
            String ssn = scanner.nextLine();

            // SQL insert statement
            
            String sqlInsert = "INSERT INTO employees (fname, lname, email, HireDate, Salary, ssn) VALUES (?, ?, ?, ?, ?, ?)";

            // Prepare and execute the statement
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
                preparedStatement.setString(1, fName);
                preparedStatement.setString(2, lName);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, hireDate);
                preparedStatement.setDouble(5, salary);
                preparedStatement.setString(6, ssn);

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Employee added successfully!");
                } else {
                    System.out.println("Failed to add employee.");
                }
            }
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
